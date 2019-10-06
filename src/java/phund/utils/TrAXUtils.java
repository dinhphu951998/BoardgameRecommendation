/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import phund.resolver.CustomURIResolver;

/**
 *
 * @author PhuNDSE63159
 */
public class TrAXUtils {

    public static TransformerFactory tf = null;
    public static CustomURIResolver resolver;
    public static java.util.Map<String, Templates> templatesMap;

    public static Templates getTemplate(String xslSrc)
            throws FileNotFoundException,
            TransformerConfigurationException {
        Templates result = null;
        tf = getTransformerFactory();

        if (templatesMap == null) {
            templatesMap = new HashMap<>();
        }
        result = templatesMap.get(xslSrc);
        if (result == null) {
            result = tf.newTemplates(new StreamSource(new File(xslSrc)));
            templatesMap.put(xslSrc, result);
        }
        return result;
    }

    private static TransformerFactory getTransformerFactory() {
        if (tf == null) {
            tf = TransformerFactory.newInstance();
        }
        return tf;
    }

    public static ByteArrayOutputStream transform(String xmlPath, String xslPath)
            throws FileNotFoundException, TransformerConfigurationException, TransformerException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        tf = getTransformerFactory();
        resolver = new CustomURIResolver();
        tf.setURIResolver(resolver);
//        URI Resolver did not call when using template in transformation
//        Templates template = factory.newTemplates(new StreamSource(new File(xslPath)));

        StreamSource source = new StreamSource(new FileInputStream(xmlPath));
        StreamResult result = new StreamResult(outputStream);

        Transformer trans = tf.newTransformer(new StreamSource(new File(xslPath)));
        trans.transform(source, result);

        return outputStream;
    }

    public static ByteArrayOutputStream transform(InputStream xmlIs, Templates template)
            throws FileNotFoundException, TransformerConfigurationException, TransformerException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        StreamSource source = new StreamSource(xmlIs);
        StreamResult result = new StreamResult(outputStream);

        Transformer trans = template.newTransformer();
        trans.transform(source, result);

        return outputStream;
    }

    public static ByteArrayOutputStream transform(InputStream xmlIs, String xslPath)
            throws FileNotFoundException, TransformerConfigurationException, TransformerException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        TransformerFactory factory = TransformerFactory.newInstance();

        StreamSource source = new StreamSource(xmlIs);
        StreamSource xslSource = new StreamSource(new FileInputStream(xslPath));
        StreamResult result = new StreamResult(outputStream);

        Transformer trans = factory.newTransformer(xslSource);
        trans.transform(source, result);

        return outputStream;
    }

//    public static void transform(String src, String dest, Templates template) {
//        try {
//            Transformer transformer = template.newTransformer();
//            StreamSource xmlFile = new StreamSource(new FileInputStream(src));
//            StreamResult destFile = new StreamResult(new FileOutputStream(dest));
//            transformer.transform(xmlFile, destFile);
//
//        } catch (FileNotFoundException | TransformerException ex) {
//            ex.printStackTrace();
//        }
//    }
//    public static void transform(String xmlSrc, String xslSrc, String dest) {
//        try {
//            TransformerFactory factory = TransformerFactory.newInstance();
//
//            CustomURIResolver resolver = new CustomURIResolver();
//            factory.setURIResolver(resolver);
//
//            File xslFile = new File(xslSrc);
//            StreamSource xsl = new StreamSource(xslFile);
//            Templates templates = factory.newTemplates(xsl);
//
//            File xmlFile = new File(xmlSrc);
//            StreamSource xml = new StreamSource(xmlFile);
//            StreamResult destFile = new StreamResult(new FileOutputStream(dest));
//
//            Transformer transformer = templates.newTransformer();
//
//            transformer.transform(xml, destFile);
//
//        } catch (FileNotFoundException | TransformerException ex) {
//            ex.printStackTrace();
//        }
//    }
//    public static DOMResult transform(String srcPath, String xslPath) {
//        DOMResult domResult = new DOMResult();
//        try {
//
//            StreamSource xsl = new StreamSource(xslPath);
//            StreamSource source = new StreamSource(srcPath);
//
//            TransformerFactory factory = TransformerFactory.newInstance();
//            CustomURIResolver resolver = new CustomURIResolver();
//            factory.setURIResolver(resolver);
//
//            Transformer trans = factory.newTransformer(xsl);
//
//            trans.transform(source, domResult);
//
//        } catch (TransformerException ex) {
//            ex.printStackTrace();
//        }
//        return domResult;
//    }
}
