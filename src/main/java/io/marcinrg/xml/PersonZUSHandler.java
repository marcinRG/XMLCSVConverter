package io.marcinrg.xml;

import io.marcinrg.interfaces.IGetPersonFromFile;
import io.marcinrg.interfaces.IGetPersonsFromFile;
import io.marcinrg.model.NameValue;
import io.marcinrg.model.Person;
import io.marcinrg.model.PersonZUS;
import io.marcinrg.utils.CheckBOM;
import org.apache.commons.io.input.BOMInputStream;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

public class PersonZUSHandler extends DefaultHandler implements IGetPersonsFromFile<PersonZUS> {
    private ArrayList<PersonZUS> personZUSArrayList;
    private PersonZUS person;
    private String currentElem;
    private String valElem;

    public int personCount = 0;
    private final String header = "row";
    private int rowCount = 0;
    private boolean isDocument;
    private boolean isPerson;
    private boolean isPersonalData;
    private boolean isData1st;
    private boolean isData2nd;
    private boolean isData3rd;
    private boolean isData;
    private int level = 0;
    private boolean isWorkData;

    public PersonZUSHandler() {
    }

    public Person getPerson() {
        return person;
    }

    private void intialize() {
        currentElem = "";
        valElem = "";
        personCount = 0;
        isDocument = false;
        isPerson = false;
        isPersonalData = false;
        isData1st = false;
        isData2nd = false;
        isData3rd = false;
        isData = false;
        isWorkData = false;
        level = 0;
        rowCount = 0;

    }

    @Override
    public void startDocument() throws SAXException {
        intialize();
    }

    @Override
    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes)
            throws SAXException {
        valElem = "";
        if (qName.equals("ZUSRCA")) {
            isDocument = true;
        }

        if (isDocument && qName.equals("III")) {
            isPerson = true;
            isPersonalData = false;
            isData1st = false;
            isData2nd = false;
            isData3rd = false;
            isData = false;
            isWorkData = false;
            person = new PersonZUS();
        }


        if (isPerson && qName.equals("A")) {
            isPersonalData = true;
        }

        if (isPerson && qName.equals("B")) {
            isData1st = true;
            rowCount = rowCount + 1;
            level = 0;
        }

        if (isPerson && qName.equals("C")) {
            isData2nd = true;
            rowCount = rowCount + 1;
        }

        if (isPerson && qName.equals("D")) {
            isData3rd = true;
            rowCount = rowCount + 1;
        }

        if (isPerson && (isData2nd || isData3rd) && qName.startsWith("p")) {
            isData = true;
        }


        if (isData1st) {
            if (qName.equals("p1") || qName.equals("p2") || qName.equals("p3")) {
                isData = false;
                level += 1;
            } else {
                isData = true;
            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("ZUSRCA")) {
            isDocument = false;
        }

        if (isPersonalData && qName.equals("p1")) {
            person.setSurName(valElem);
        }

        if (isPersonalData && qName.equals("p2")) {
            person.setName(valElem);
        }

        if (isPersonalData && qName.equals("p4")) {
            person.setPESEL(valElem);
        }


        if (isPerson && qName.equals("III")) {
            personZUSArrayList.add(person);
            isPerson = false;
        }

        if (isPersonalData && qName.equals("A")) {
            isPersonalData = false;
        }

        if (isPerson && qName.equals("B")) {
            isData1st = false;
        }

        if (isPerson && qName.equals("C")) {
            isData2nd = false;
        }

        if (isPerson && qName.equals("D")) {
            isData3rd = false;
        }

        if (level > 1) {
            if (isWorkData) {
                person.addTime(new BigDecimal(valElem));
            } else {
                person.setDisabilityCode(person.getDisabilityCode() + valElem);
            }
        }

        if (isData1st && (qName.equals("p1") || qName.equals("p2") || qName.equals("p3"))) {
            level -= 1;
            if (qName.endsWith("p3")) {
                isWorkData = true;
            }
        }

        if ((isData2nd || isData1st ||isData3rd) && isData) {
            String newName = header + rowCount + qName;
            person.addValue(new NameValue(newName,new BigDecimal(valElem)));
            isData = false;
        }
        valElem = "";
    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
        String value = new String(chars, start, length);
        valElem += value;
    }

    @Override
    public ArrayList<PersonZUS> getPersonsFromFile(File file) throws IOException, SAXException, ParserConfigurationException {
        BOMInputStream inputStream = CheckBOM.getStream(file);
        return getPersonsFromInputSteam(inputStream);
    }

    @Override
    public ArrayList<PersonZUS> getPersonsFromInputSteam(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        personZUSArrayList = new ArrayList<>();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(inputStream, this);
        return personZUSArrayList;
    }
}