/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import com.sun.xml.bind.StringInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import phund.utils.StAXUtils;

/**
 *
 * @author PhuNDSE63159
 */
public class StringProcessor extends Parser {

    private InputStream inputStream;
    private XMLEventWriter writer;
    private XMLEventReader reader;
    private OutputStream outputStream;
    private StringInputStream sis;
    private int totalGame;

    public int getTotalGame() {
        return totalGame;
    }

    private XMLEventFactory eventFactory;

    public StringProcessor(InputStream inputStream) {
        this.inputStream = inputStream;
        eventFactory = XMLEventFactory.newFactory();
    }

    @Override
    public OutputStream parse() throws XMLStreamException {
        reader = StAXUtils.getXMLEventReader(inputStream);
        outputStream = new ByteArrayOutputStream();
        writer = StAXUtils.getXMLEventWriter(outputStream);
        XMLEvent event;
        while (reader.hasNext()) {
            event = reader.nextEvent();
            writer.add(event);
            if (event.isStartElement()) {
                if (event.asStartElement().getName().getLocalPart().equals("game")) {
                    totalGame++;
                }
                switch (event.asStartElement().getName().getLocalPart()) {
                    case "numPlayer":
                        parseNumPlayerElement(event);
                        break;
                    case "time":
                        parseTimeElement(event);
                        break;
                    case "age":
                        parseAgeElement(event);
                        break;
                    default:
                        break;

                } // end switch case

            } //end if start element

        }//end while reader has next
        writer.flush();
        return outputStream;
    }

    private void parseNumPlayerElement(XMLEvent event) throws XMLStreamException {
        event = reader.nextEvent();
        if (event.isCharacters()) {
            Characters chars = event.asCharacters();
            String players = chars.getData();
            players = players.replaceAll("[^0-9]", " ").trim();
            if (!"".equals(players)) {
                String[] minMaxPlayer = players.split(" ");

                writer.add(eventFactory.createStartElement("", null, "minPlayer"));
                writer.add(eventFactory.createCharacters(minMaxPlayer[0]));
                writer.add(eventFactory.createEndElement("", null, "minPlayer"));

                if (minMaxPlayer.length == 2) {
                    writer.add(eventFactory.createStartElement("", null, "maxPlayer"));
                    writer.add(eventFactory.createCharacters(minMaxPlayer[1]));
                    writer.add(eventFactory.createEndElement("", null, "maxPlayer"));
                }
//                System.out.println(xml);

            }//end if not empty numplayer
            event = reader.nextTag();
        }//end if isChar
        writer.add(event);
    }

    private void parseTimeElement(XMLEvent event) throws XMLStreamException {

        event = reader.nextEvent();
        if (event.isCharacters()) {
            Characters chars = event.asCharacters();
            String time = chars.getData();
            time = time.replaceAll("[^0-9]", " ").trim();
            if (!"".equals(time)) {
                String[] minMaxTime = time.split(" ");

                writer.add(eventFactory.createStartElement("", null, "minTime"));
                writer.add(eventFactory.createCharacters(minMaxTime[0]));
                writer.add(eventFactory.createEndElement("", null, "minTime"));

                if (minMaxTime.length == 2) {
                    writer.add(eventFactory.createStartElement("", null, "maxTime"));
                    writer.add(eventFactory.createCharacters(minMaxTime[1]));
                    writer.add(eventFactory.createEndElement("", null, "maxTime"));
                }

            }//end if not empty time
            event = reader.nextTag();
        }//end if isChar
        writer.add(event);
    }

    private void parseAgeElement(XMLEvent event) throws XMLStreamException {
        event = reader.nextEvent();
        if (event.isCharacters()) {
            Characters chars = event.asCharacters();
            String age = chars.getData();
            age = age.replaceAll("[^0-9]", " ").trim();
            if (!"".equals(age)) {
                String[] minMaxAge = age.split(" ");

                writer.add(eventFactory.createStartElement("", null, "minAge"));
                writer.add(eventFactory.createCharacters(minMaxAge[0]));
                writer.add(eventFactory.createEndElement("", null, "minAge"));

                if (minMaxAge.length == 2) {

                    writer.add(eventFactory.createStartElement("", null, "maxAge"));
                    writer.add(eventFactory.createCharacters(minMaxAge[1]));
                    writer.add(eventFactory.createEndElement("", null, "maxAge"));
                }

            }//end if not empty age
            event = reader.nextTag();
        }//end if isChar
        writer.add(event);
    }

}
