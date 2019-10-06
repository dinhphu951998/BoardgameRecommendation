/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import org.xml.sax.SAXException;
import phund.constant.FileConstant;
import phund.entity.BoardGame;
import phund.entity.Game;
import phund.entity.Image;
import phund.repository.GameRepository;
import phund.repository.GameRepositoryImp;
import phund.repository.ImageRepository;
import phund.repository.ImageRepositoryImp;
import phund.resolver.CustomURIResolver;
import phund.utils.DateUtils;
import phund.utils.FileUtils;
import phund.utils.JAXBUtils;
import phund.utils.TrAXUtils;
import static phund.utils.TrAXUtils.resolver;
import static phund.utils.TrAXUtils.tf;
import phund.utils.XmlUtils;
import phund.validation.BoardGameValidationHandler;

/**
 *
 * @author PhuNDSE63159
 */
public class CrawlService {

    final String[] inputUrls = new String[]{
        FileConstant.INPUT_BOARD_GAME_BLISS, //        FileConstant.INPUT_BOARD_GAME_HUB,
    //        FileConstant.INPUT_BOARD_GAMING,
    //        FileConstant.INPUT_HOC_VIEN_BOARD_GAME
    };

    final String[] outputUrls = new String[]{
        FileConstant.OUTPUT_BOARD_GAME_BLISS, //        FileConstant.OUTPUT_BOARD_GAME_HUB,
    //        FileConstant.OUTPUT_BOARD_GAMING,
    //        FileConstant.OUTPUT_HOC_VIEN_BOARD_GAME
    };

    final String[] xslUrls = new String[]{
        FileConstant.XSL_BOARD_GAME_BLISS, //        FileConstant.XSL_BOARD_GAME_HUB,
    //        FileConstant.XSL_BOARD_GAMING,
    //        FileConstant.XSL_HOC_VIEN_BOARD_GAME
    };

    private String baseUrl;

    public CrawlService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void startCrawler()
            throws IOException, FileNotFoundException, TransformerException, XMLStreamException, JAXBException, SAXException {
        for (int i = 0; i < inputUrls.length; i++) {
            String inputUrl = baseUrl + inputUrls[i];
            String outputUrl = baseUrl + outputUrls[i];
            String xslUrl = baseUrl + xslUrls[i];
            System.out.println(inputUrl);
            System.out.println(outputUrl);
            System.out.println(xslUrl);
            System.out.println("");
            //crawl from website
            ByteArrayOutputStream os = TrAXUtils.transform(inputUrl, xslUrl);

            //save to file 1
            FileUtils.writeFile(outputUrl, os.toByteArray());
            System.out.println("Save file success");

            //process string
            InputStream inputStream = new ByteArrayInputStream(os.toByteArray());
            StringProcessor stringProcessor = new StringProcessor(inputStream);
            os = (ByteArrayOutputStream) stringProcessor.parse();

            //apply last filter
            inputStream = new ByteArrayInputStream(os.toByteArray());
            Templates template = TrAXUtils.getTemplate(baseUrl + FileConstant.NORMALIZER);
            os = TrAXUtils.transform(inputStream, template);

            //validator
            Schema schema = XmlUtils.getSchema(baseUrl + FileConstant.BOARDGAME_SCHEMA);
            BoardGameValidationHandler errorHandler = new BoardGameValidationHandler();

            //unmarshal
            ByteArrayInputStream bis = new ByteArrayInputStream(os.toByteArray());
            BoardGame boardGame = (BoardGame) JAXBUtils.unmarshalBoardgame(bis, errorHandler, schema, BoardGame.class);

            if (errorHandler.getErrorCounter() != 0) {
                String docFile = baseUrl + FileConstant.BASE_LOG_FILE
                        + String.format("boardgame-document-%s.xml", DateUtils.getTodayString());
                String errorFile = baseUrl + FileConstant.BASE_LOG_FILE
                        + String.format("boardgame-error-%s.xml", DateUtils.getTodayString());
                System.out.println(docFile);
                System.out.println(errorFile);
                FileUtils.writeFile(docFile, os.toByteArray());
                FileUtils.writeFile(errorFile,
                        ((ByteArrayOutputStream) errorHandler.getErrorStream()).toByteArray());
                System.out.println("Error saved");
            }

            //save to db
            GameRepository gameRepository = new GameRepositoryImp();
            gameRepository.createRange(boardGame.getGames());
            System.out.println("Save to db");
        }
    }

}
