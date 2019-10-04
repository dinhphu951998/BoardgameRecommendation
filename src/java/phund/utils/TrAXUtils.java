/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import phund.resolver.CustomURIResolver;

/**
 *
 * @author PhuNDSE63159
 */
public class TrAXUtils {

    public static TransformerFactory tf = null;

    public static Templates getTemplate(String xslSrc) {
        try {
            Templates result = null;
            TransformerFactory tf = TransformerFactory.newInstance();
            CustomURIResolver resolver = new CustomURIResolver();
            tf.setURIResolver(resolver);
            result = tf.newTemplates(new StreamSource(xslSrc));
            return result;
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(TrAXUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void transform(String src, String dest, Templates template) {
        try {
            Transformer transformer = template.newTransformer();
            StreamSource xmlFile = new StreamSource(src);
            StreamResult destFile = new StreamResult(new FileOutputStream(dest));
            transformer.transform(xmlFile, destFile);

        } catch (FileNotFoundException | TransformerException ex) {
            ex.printStackTrace();
        }
    }

    public static void transform(String xmlSrc, String xslSrc, String dest) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            CustomURIResolver resolver = new CustomURIResolver();
            factory.setURIResolver(resolver);

            StreamSource xsl = new StreamSource(xslSrc);
            StreamSource xmlFile = new StreamSource(xmlSrc);
            StreamResult destFile = new StreamResult(new FileOutputStream(dest));

            Transformer transformer = factory.newTransformer(xsl);

            transformer.transform(xmlFile, destFile);

        } catch (FileNotFoundException | TransformerException ex) {
            ex.printStackTrace();
        }
    }

    public static DOMResult transform(String srcPath, String xslPath) {
        DOMResult domResult = new DOMResult();
        try {

            StreamSource xsl = new StreamSource(xslPath);
            StreamSource source = new StreamSource(srcPath);

            TransformerFactory factory = TransformerFactory.newInstance();
            CustomURIResolver resolver = new CustomURIResolver();
            factory.setURIResolver(resolver);

            Transformer trans = factory.newTransformer(xsl);

            trans.transform(source, domResult);

        } catch (TransformerException ex) {
            ex.printStackTrace();
        }
        return domResult;
    }

}
