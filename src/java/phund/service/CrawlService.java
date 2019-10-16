/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Templates;
import javax.xml.validation.Schema;
import phund.constant.FileConstant;
import phund.entity.BoardGame;
import phund.entity.Game;
import phund.entity.WrapperDuplicatedGame;
import phund.repository.GameRepository;
import phund.repository.GameRepositoryImp;
import phund.utils.DateUtils;
import phund.utils.FileUtils;
import phund.utils.JAXBUtils;
import phund.utils.TrAXUtils;
import phund.utils.XmlUtils;
import phund.handler.BoardGameValidationHandler;

/**
 *
 * @author PhuNDSE63159
 */
public class CrawlService {

    final String[] inputUrls = new String[]{
        FileConstant.INPUT_BOARD_GAME_BLISS,
        FileConstant.INPUT_BOARD_GAME_HUB,
        FileConstant.INPUT_BOARD_GAMING,
        FileConstant.INPUT_HOC_VIEN_BOARD_GAME
    };

    final String[] outputUrls = new String[]{
        FileConstant.OUTPUT_BOARD_GAME_BLISS,
        FileConstant.OUTPUT_BOARD_GAME_HUB,
        FileConstant.OUTPUT_BOARD_GAMING,
        FileConstant.OUTPUT_HOC_VIEN_BOARD_GAME
    };

    final String[] xslUrls = new String[]{
        FileConstant.XSL_BOARD_GAME_BLISS,
        FileConstant.XSL_BOARD_GAME_HUB,
        FileConstant.XSL_BOARD_GAMING,
        FileConstant.XSL_HOC_VIEN_BOARD_GAME
    };

    final String[] webNames = new String[]{
        "boardgamebliss",
        "boardgamehub",
        "boardgaming",
        "hocvienboardgame"
    };

    private String baseUrl;

    public CrawlService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void startCrawler() {
        for (int i = 0; i < inputUrls.length; i++) {
            try {
                Thread t = new Thread(new ThreadCrawl(baseUrl, i));
                t.start();;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class ThreadCrawl implements Runnable {

        private String baseUrl;
        private int i;

        private GameRepository gameRepository;
        private List<Game> savedGames;
        private WrapperDuplicatedGame wrapperDuplicatedGame;

        private ByteArrayOutputStream os = null;
        private ByteArrayInputStream is = null;

        public ThreadCrawl(String baseUrl, int i) {
            this.baseUrl = baseUrl;
            this.i = i;
        }

        @Override
        public void run() {
            try {
                crawl(baseUrl, i);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        void crawl(String baseUrl, int i) {
            try {
                gameRepository = new GameRepositoryImp();

                String inputUrl = baseUrl + inputUrls[i];
                String outputUrl = baseUrl + outputUrls[i];
                String xslUrl = baseUrl + xslUrls[i];
                System.out.println(inputUrl);
                System.out.println(outputUrl);
                System.out.println(xslUrl);
                System.out.println("");

                //crawl from website
                os = TrAXUtils.transform(inputUrl, xslUrl);

                //--save to file--
                FileUtils.writeFile(outputUrl, os, false);
                System.out.println(webNames[i] + " file saved");

                //process string
                is = new ByteArrayInputStream(os.toByteArray());
                StringProcessor stringProcessor = new StringProcessor(is);
                os = (ByteArrayOutputStream) stringProcessor.parse();

                //apply last filter
                is = new ByteArrayInputStream(os.toByteArray());
                Templates template = TrAXUtils.getTemplate(baseUrl + FileConstant.NORMALIZER);
                os = TrAXUtils.transform(is, template);

                //validator
                Schema schema = XmlUtils.getSchema(baseUrl + FileConstant.BOARDGAME_SCHEMA);
                BoardGameValidationHandler errorHandler = new BoardGameValidationHandler();

                //unmarshal
                ByteArrayInputStream bis = new ByteArrayInputStream(os.toByteArray());
                BoardGame boardGame = (BoardGame) JAXBUtils.unmarshalBoardgame(bis, errorHandler, schema, BoardGame.class);
                if (errorHandler.getErrorCounter() != 0) {
                    String today = DateUtils.getTodayString();
                    String docFile = baseUrl + FileConstant.BASE_LOG_FILE
                            + String.format("%s-document-%s.xml", webNames[i], today);
                    String errorFile = baseUrl + FileConstant.BASE_LOG_FILE
                            + String.format("%s-error-%s.xml", webNames[i], today);
                    System.out.println(docFile);
                    System.out.println(errorFile);
                    FileUtils.writeFile(docFile, os, false);
                    FileUtils.writeFile(errorFile, errorHandler.getErrorStream(), false);
                }

                saveToDb(boardGame);
                saveDuplicatedGame();

                int total = boardGame.getGames().size();
                int duplicated = 0;
                if (wrapperDuplicatedGame != null) {
                    duplicated = wrapperDuplicatedGame.size();
                }
                int count = total - duplicated;

                System.out.println(webNames[i] + " - total after string process: " + stringProcessor.getTotalGame());
                System.out.println(webNames[i] + " - total after jaxb: " + total);
                System.out.println(webNames[i] + " - save: " + count);
                System.out.println(webNames[i] + " - duplicate: " + duplicated);
                System.out.println(webNames[i] + " - error: " + errorHandler.getErrorCounter());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private void saveToDb(BoardGame boardGame) {
            //save to db
            if (boardGame != null && boardGame.getGames() != null) {
                for (Game game : boardGame.getGames()) {
                    Game duplicatedGame = checkDuplicate(game.getTitle());
                    if (duplicatedGame == null) {
                        gameRepository.create(game);
                        savedGames.add(game);
                    } else {
                        if (this.wrapperDuplicatedGame == null) {
                            wrapperDuplicatedGame = new WrapperDuplicatedGame();
                        }
                        wrapperDuplicatedGame.add(game, duplicatedGame);
                    }
                }
            }
        }

        private void saveDuplicatedGame() throws JAXBException {
            if (wrapperDuplicatedGame == null) {
                return;
            }
            String content = JAXBUtils.marshal(wrapperDuplicatedGame, WrapperDuplicatedGame.class);
            FileUtils.writeFile(baseUrl + FileConstant.DUPLICATED_GAMES_FILE, content, false);
        }

        //check duplicate using LCS
        private Game checkDuplicate(String name) {
            if (savedGames == null) {
                savedGames = gameRepository.findManyAll("Game.findAll", null);
            }
            for (Game dbGame : savedGames) {
                int matchingPoint = XmlUtils.computeMatchingPercent(name, dbGame.getTitle());
                if (matchingPoint >= 80) {
                    return dbGame;
                }
            }
            return null;
        }
    }

}
