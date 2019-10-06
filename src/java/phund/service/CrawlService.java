/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerException;
import phund.constant.FileConstant;
import phund.entity.BoardGame;
import phund.utils.FileUtils;
import phund.utils.JAXBUtils;
import phund.utils.TrAXUtils;

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
            throws IOException, FileNotFoundException, TransformerException, XMLStreamException, JAXBException {
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
            InputStream inputStream = new ByteArrayInputStream(os.toByteArray());
            //save to file 1
            FileUtils.writeFile(outputUrl, os);
            System.out.println("Save file success");

            //process string
            StringProcessor stringProcessor = new StringProcessor(inputStream);
            os = (ByteArrayOutputStream) stringProcessor.parse();

            //save to file 2
            FileUtils.writeFile(outputUrl, os);
            System.out.println("Save file 2 success");

            //validator
            //apply last filter
            Templates template = TrAXUtils.getTemplate(baseUrl + FileConstant.NORMALIZER);
            os = TrAXUtils.transform(new ByteArrayInputStream(os.toByteArray()), template);

            //save to file 3
            FileUtils.writeFile(outputUrl, os);
            System.out.println("Save file 3 success");

            //unmarshal
            BoardGame boardGame = (BoardGame) 
                    JAXBUtils.unmarshal(new ByteArrayInputStream(os.toByteArray()), BoardGame.class);

            //save to db
            
            System.out.println(boardGame.getGames());
            System.out.println("");
        }
    }

}
