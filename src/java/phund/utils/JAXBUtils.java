/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.utils;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.ConsoleErrorReporter;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author PhuNDSE63159
 */
public class JAXBUtils {

    public static Object unmarshal(String xmlFile, Class classType) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(classType);
        Unmarshaller u = context.createUnmarshaller();
        return u.unmarshal(new StreamSource(xmlFile));
    }

    public static Object unmarshal(Node node, Class classType) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(classType);
        Unmarshaller u = context.createUnmarshaller();
        return u.unmarshal(new DOMSource(node));
    }

    public static Object unmarshal(InputStream is, Class classType) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(classType);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(is);
    }

    public static void marshal(String xmlFile, Object data, Class type) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(type);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(data, new FileOutputStream(xmlFile));
    }

    public static void marshal(Object data, Node result, Class type) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(type);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(data, result);
    }

    public static void generateJavaClass(String output, String packageName, String schemaFilePath) throws IOException {
        SchemaCompiler sc = XJC.createSchemaCompiler();
        sc.setErrorListener(new ConsoleErrorReporter());

        sc.forcePackageName(packageName);

        File f = new File(schemaFilePath);
        sc.parseSchema(new InputSource(f.toURI().toString()));

        S2JJAXBModel model = sc.bind();
        JCodeModel code = model.generateCode(null, null);
        code.build(new File(output));
    }

}
