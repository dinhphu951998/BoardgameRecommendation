package xml.test;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package xml.parserxslt;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import javax.xml.stream.XMLStreamException;
//import javax.xml.transform.Templates;
//import javax.xml.transform.TransformerConfigurationException;
//import javax.xml.validation.Schema;
//import phund.constant.FileConstant;
//import phund.entity.BoardGame;
//import phund.entity.Game;
//import phund.service.StringProcessor;
//import phund.utils.DateUtils;
//import phund.utils.FileUtils;
//import phund.utils.JAXBUtils;
//import phund.utils.TrAXUtils;
//import phund.utils.XmlUtils;
//import phund.validation.BoardGameValidationHandler;
//
///**
// *
// * @author PhuNDSE63159
// */
//public class TestUnmarshal {
//
//    private static String output
//            = "H:\\FPTU\\8\\XML\\practice\\BoardgameRecommendation\\build\\web\\WEB-INF/boardgamebliss/output-boardgamebliss.xml";
//
//    private static String baseUrl = "H:\\FPTU\\8\\XML\\practice\\BoardgameRecommendation\\build\\web\\";
//
//    public static void main() throws Exception {
//        InputStream inputStream = new FileInputStream(output);
//        //process string
//        StringProcessor stringProcessor = new StringProcessor(inputStream);
//        ByteArrayOutputStream os = (ByteArrayOutputStream) stringProcessor.parse();
//        //apply last filter
//        inputStream = new ByteArrayInputStream(os.toByteArray());
//        Templates template = TrAXUtils.getTemplate(baseUrl + FileConstant.NORMALIZER);
//        os = TrAXUtils.transform(inputStream, template);
//        //validator
//        Schema schema = XmlUtils.getSchema(baseUrl + FileConstant.BOARDGAME_SCHEMA);
//        BoardGameValidationHandler errorHandler = new BoardGameValidationHandler();
//        //unmarshal
//        ByteArrayInputStream bis = new ByteArrayInputStream(os.toByteArray());
//        BoardGame boardGame = (BoardGame) JAXBUtils.unmarshalBoardgame(bis, errorHandler, schema, BoardGame.class);
//        if (errorHandler.getErrorCounter() != 0) {
//            String today = DateUtils.getTodayString();
//            String docFile = baseUrl + FileConstant.BASE_LOG_FILE
//                    + String.format("%s-document-%s.xml", "boardgamebliss", today);
//            String errorFile = baseUrl + FileConstant.BASE_LOG_FILE
//                    + String.format("%s-error-%s.xml", "boardgamebliss", today);
//            System.out.println(docFile);
//            System.out.println(errorFile);
//            FileUtils.writeFile(docFile, os.toByteArray());
//            FileUtils.writeFile(errorFile,
//                    ((ByteArrayOutputStream) errorHandler.getErrorStream()).toByteArray());
//        }
//
//        int count = 0;
//        int total = 0;
//        int duplicated = 0;
//        //save to db
//        if (boardGame != null && boardGame.getGames() != null) {
//            total = boardGame.getGames().size();
//            for (Game game : boardGame.getGames()) {
//                if (!checkDuplicate(game.getTitle())) {
//                    gameRepository.create(game);
//                    count++;
//                } else {
//                    duplicated++;
//                }
//            }
//        }
//
//        System.out.println("i: " + i + " - total: " + total);
//        System.out.println("i: " + i + " - save: " + count);
//        System.out.println("i: " + i + " - duplicate: " + duplicated);
//        System.out.println("i: " + i + " - error: " + errorHandler.getErrorCounter());
//
//    }
//
//}
