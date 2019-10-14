package xml.parserxslt;

import com.sun.xml.bind.StringInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import phund.constant.FileConstant;
import phund.entity.Game;
import phund.entity.ItemBasedPoint;
import phund.entity.ItemBasedPointPK;
import phund.entity.WrapperDuplicatedGame;
import phund.repository.ItemBasedRepository;
import phund.repository.ItemBasedRepositoryImp;
import phund.service.CrawlService;
import phund.utils.FileUtils;
import phund.utils.JAXBUtils;
import phund.utils.StAXUtils;

@XmlRootElement
class MapWrapper {

    private java.util.Map<Integer, String> map;

    public MapWrapper() {
    }

    public MapWrapper(Map<Integer, String> map) {
        this.map = map;
    }

    public void setMap(Map<Integer, String> map) {
        this.map = map;
    }

    public Map<Integer, String> getMap() {
        return map;
    }

}

public class TestCrawl {

    public static void main(String[] args) throws Exception {
        findByPK();
    }

    public static void findByPK() {
        ItemBasedRepository repository = new ItemBasedRepositoryImp();
        ItemBasedPoint item = repository.findById(new ItemBasedPointPK(60, 100));
        System.out.println(item);
    }

    static void testMarshallMap() throws JAXBException {
        WrapperDuplicatedGame wrapperDuplicatedGame = new WrapperDuplicatedGame();
        Game g = new Game();
        g.setTitle("Nguyễn Đình Phú");
        g.setThumbnail("Bình Dương");
        Game duplicate = new Game(1);
        duplicate.setTitle("Nguyễn Đình Bảo");
        duplicate.setThumbnail("Thành phố Hồ Chí Minh");
        wrapperDuplicatedGame.add(g, duplicate);
        if (wrapperDuplicatedGame == null) {
            return;
        }
        String data = JAXBUtils.marshal(wrapperDuplicatedGame, WrapperDuplicatedGame.class);
        System.out.println(data);
    }

    public static void testRepository() {
        ItemBasedRepository repository = new ItemBasedRepositoryImp();
        List<ItemBasedPoint> ibps = repository.findMany("ItemBasedPoint.findAll", null, null, null);
        System.out.println("");
    }

    public static void testCrawl() throws Exception {
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
