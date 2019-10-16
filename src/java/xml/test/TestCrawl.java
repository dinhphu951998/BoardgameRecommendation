package xml.test;

import com.sun.xml.bind.StringInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.xml.transform.Templates;
import javax.xml.validation.Schema;
import phund.constant.FileConstant;
import phund.entity.BoardGame;
import phund.entity.Game;
import phund.entity.ItemBasedPoint;
import phund.entity.ItemBasedPointPK;
import phund.entity.WrapperDuplicatedGame;
import phund.handler.BoardGameValidationHandler;
import phund.repository.GameRepository;
import phund.repository.GameRepositoryImp;
import phund.repository.ItemBasedRepository;
import phund.repository.ItemBasedRepositoryImp;
import phund.service.CrawlService;
import phund.service.StringProcessor;
import phund.utils.DateUtils;
import phund.utils.FileUtils;
import phund.utils.JAXBUtils;
import phund.utils.StAXUtils;
import phund.utils.TrAXUtils;
import phund.utils.XmlUtils;

public class TestCrawl {

    private static GameRepository gameRepository = new GameRepositoryImp();
    private static WrapperDuplicatedGame wrapperDuplicatedGame = new WrapperDuplicatedGame();
    static String baseUrl = "H:\\FPTU\\8\\XML\\practice\\BoardgameRecommendation\\build\\web\\";
    static List<Game> savedGames;

    public static void main(String[] args) throws Exception {
        testJAXB();
    }
    
    
    public static void testJAXB() throws FileNotFoundException, JAXBException{
        String filename = "H:\\FPTU\\8\\XML\\practice\\BoardgameRecommendation\\web\\WEB-INF\\xsl\\tmp.xml";
        FileInputStream is = new FileInputStream(filename);
        BoardGame boardGame = (BoardGame) JAXBUtils.unmarshal(is, BoardGame.class);
        System.out.println(boardGame);
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

    public static void testCrawl(String url) throws Exception {

//        String content = FileUtils.read(url);
        InputStream is = new FileInputStream(url);
        StringProcessor stringProcessor = new StringProcessor(is);
        ByteArrayOutputStream os = (ByteArrayOutputStream) stringProcessor.parse();

        //process string
//        is = new ByteArrayInputStream(os.toByteArray());
//        StringProcessor stringProcessor = new StringProcessor(is);
//        os = (ByteArrayOutputStream) stringProcessor.parse();
        //apply last filter
        is = new ByteArrayInputStream(os.toByteArray());
        Templates template = TrAXUtils.getTemplate(baseUrl + FileConstant.NORMALIZER);
        os = TrAXUtils.transform(is, template);

        //validator
        Schema schema = XmlUtils.getSchema(baseUrl + FileConstant.BOARDGAME_SCHEMA);
        BoardGameValidationHandler errorHandler = new BoardGameValidationHandler();

        //unmarshal
        ByteArrayInputStream bis = new ByteArrayInputStream(os.toByteArray());
        BoardGame boardGame = (BoardGame) JAXBUtils.unmarshalBoardgame(bis, errorHandler, schema, BoardGame.class);
        if (errorHandler.getErrorCounter() != 0) {
            System.out.println("ERror in unmarshaaling");
        }

        saveToDb(boardGame);
        saveDuplicatedGame();

        int total = boardGame.getGames().size();
        int duplicated = 0;
        if (wrapperDuplicatedGame != null) {
            duplicated = wrapperDuplicatedGame.size();
        }
        int count = total - duplicated;

        System.out.println(" - total after string process: " + stringProcessor.getTotalGame());
        System.out.println(" - total after jaxb: " + total);
        System.out.println(" - save: " + count);
        System.out.println(" - duplicate: " + duplicated);
        System.out.println(" - error: " + errorHandler.getErrorCounter());

    }

    private static void saveToDb(BoardGame boardGame) {
        //save to db
        if (boardGame != null && boardGame.getGames() != null) {
            for (Game game : boardGame.getGames()) {
                Game duplicatedGame = checkDuplicate(game.getTitle());
                if (duplicatedGame == null) {
                    gameRepository.create(game);
                    savedGames.add(game);
                } else {
                    if (wrapperDuplicatedGame == null) {
                        wrapperDuplicatedGame = new WrapperDuplicatedGame();
                    }
                    wrapperDuplicatedGame.add(game, duplicatedGame);
                }
            }
        }
    }

    private static void saveDuplicatedGame() throws JAXBException {
        if (wrapperDuplicatedGame == null) {
            return;
        }
        String content = JAXBUtils.marshal(wrapperDuplicatedGame, WrapperDuplicatedGame.class);
        FileUtils.writeFile(baseUrl + FileConstant.DUPLICATED_GAMES_FILE, content, false);
    }

    //check duplicate using LCS
    private static Game checkDuplicate(String name) {
        if (savedGames == null) {
            savedGames = gameRepository.findManyAll("Game.findAll", null);
        }
        for (Game dbGame : savedGames) {
            int matchingPoint = XmlUtils.computeMatchingPercent(name, dbGame.getTitle());
            if (matchingPoint >= 80) {
                return dbGame;
            }
        }
        return null;
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
