/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.parserxslt;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import phund.utils.FileUtils;
import phund.utils.HttpUtils;
import phund.utils.TrAXUtils;

/**
 *
 * @author PhuNDSE63159
 */
public class ParserXSLT {

    public static String xmlSrc = "src/xml/parser/boardgamebliss/input-boardgamebliss.xml";
    public static String xslSrc = "src/xml/parser/boardgamebliss/boardgamebliss-main.xsl";
    public static String xmlResult = "src/xml/parser/boardgamebliss/output-boardgamebliss.xml";

    public static void main(String[] args) {
        TrAXUtils.transform(xmlSrc, xslSrc, xmlResult);

//        Templates template = TrAXUtils.getTemplate(xslSrc);
//        TrAXUtils.transform(xmlSrc, xmlResult, template);
//        String url = "https://boardgame.vn/";
//        String data = HttpUtils.getHttpContent(url);
//        System.out.println(data);
//        try {
//            DOMResult rs = TrAXUtils.transform(xmlSrc, xslSrc);
//
//            TransformerFactory factory = TransformerFactory.newInstance();
//            StreamResult sr = new StreamResult(new FileOutputStream(xmlResult));
//            Transformer trans = factory.newTransformer();
//            trans.transform(new DOMSource(rs.getNode()), sr);
//
//        } catch (FileNotFoundException | TransformerException ex) {
//            ex.printStackTrace();
//        }
    }
}
