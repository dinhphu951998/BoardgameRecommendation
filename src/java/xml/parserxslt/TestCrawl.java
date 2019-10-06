/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.parserxslt;

import com.sun.xml.bind.StringInputStream;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import phund.service.CrawlService;
import phund.utils.StAXUtils;

/**
 *
 * @author PhuNDSE63159
 */
public class TestCrawl {

    public static void main(String[] args) throws Exception {

        String base = "H:\\FPTU\\8\\XML\\practice\\BoardgameRecommendation\\build\\web\\";
        
        CrawlService service = new CrawlService(base);
        service.startCrawler();
    }

    public static void testSingleElement() throws XMLStreamException {
        String xml = "<a><b/></a>";
        StringInputStream sis = new StringInputStream(xml);
        XMLEventReader reader = StAXUtils.getXMLEventReader(sis);
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("b")) {
                event = reader.nextEvent();
                String value = event.asCharacters().getData();
                System.out.println("");
            }
            System.out.println("");

        }
    }
}
