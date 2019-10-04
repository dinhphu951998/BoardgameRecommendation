/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.crawler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
public class Crawler {

    public void crawl(String xmlSrc, String xslSrc, String dest) 
            throws FileNotFoundException, TransformerConfigurationException, TransformerException {

        TransformerFactory factory = TransformerFactory.newInstance();
        CustomURIResolver resolver = new CustomURIResolver();
        factory.setURIResolver(resolver);

        StreamSource xsl = new StreamSource(xslSrc);
        StreamSource xmlFile = new StreamSource(xmlSrc);
        StreamResult destFile = new StreamResult(new FileOutputStream(dest));

        Transformer transformer = factory.newTransformer(xsl);

        transformer.transform(xmlFile, destFile);

    }

}
