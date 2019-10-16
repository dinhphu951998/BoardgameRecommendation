/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

    public synchronized static Templates getTemplate(String xslSrc)
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

    private synchronized static TransformerFactory getTransformerFactory() {
        if (tf == null) {
            tf = TransformerFactory.newInstance();
        }
        return tf;
    }

    public static ByteArrayOutputStream transform(String xmlPath, String xslPath)
            throws FileNotFoundException, TransformerConfigurationException, TransformerException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        TransformerFactory tf = getTransformerFactory();
        CustomURIResolver resolver = new CustomURIResolver();
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

    //using in crawl process
    public static ByteArrayOutputStream transform(InputStream xmlIs, String xslPath)
            throws FileNotFoundException, TransformerConfigurationException, TransformerException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        TransformerFactory factory = getTransformerFactory();

        StreamSource source = new StreamSource(xmlIs);
        StreamSource xslSource = new StreamSource(new FileInputStream(xslPath));
        StreamResult result = new StreamResult(outputStream);

        Transformer trans = factory.newTransformer(xslSource);
        trans.transform(source, result);

        return outputStream;
    }

}
