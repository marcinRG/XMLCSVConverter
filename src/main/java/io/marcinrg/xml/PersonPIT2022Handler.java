package io.marcinrg.xml;

import io.marcinrg.interfaces.IGetPersonFromFile;
import io.marcinrg.model.NameValue;
import io.marcinrg.model.PersonPIT;
import io.marcinrg.utils.CheckBOM;
import org.apache.commons.io.input.BOMInputStream;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class PersonPIT2022Handler extends DefaultHandler implements IGetPersonFromFile<PersonPIT> {

    private PersonPIT person;
    private String currentElem;
    private String valElem;

    //flags
    private boolean isPerson = false;
    private boolean isAddress = false;
    private boolean isData = false;

    public PersonPIT2022Handler() {
    }

    private void intialize() {
        person = new PersonPIT();
        System.out.println(person.getDataAsString("|",false));
        currentElem = "";
        valElem = "";
    }

    @Override
    public void startDocument() throws SAXException {
        intialize();
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equals("Podmiot2")) {
            isPerson = true;
        }
        if (qName.equals("AdresZamieszkania")) {
            if (isPerson) isAddress = true;
            person.getAddress().setStreetName("puste");
        }


        if (qName.equals("ImiePierwsze")) {
            if (isPerson) {
                currentElem = qName;
            }
        }
        if (qName.equals("Nazwisko")) {
            if (isPerson) {
                currentElem = qName;
            }
        }

        if (qName.equals("Miejscowosc")) {
            if (isAddress) currentElem = qName;
        }
        if (qName.equals("Ulica")) {
            if (isAddress) currentElem = qName;
        }
        if (qName.equals("NrDomu")) {
            if (isAddress) currentElem = qName;
        }

        if (qName.equals("PozycjeSzczegolowe")) {
            isData = true;
        }

        if (qName.startsWith("P_")) {
            if (isData) {
                currentElem = qName;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (currentElem.equals("ImiePierwsze")) {
            if (isPerson) {
                person.setName(valElem);
                currentElem = "";
                valElem = "";
            }
        }

        if (qName.equals("Nazwisko")) {
            if (isPerson) {
                person.setSurName(valElem);
                currentElem = "";
                valElem = "";
            }
        }

        if (qName.equals("Miejscowosc")) {
            if (isAddress) {
                person.getAddress().setCity(valElem);
                currentElem = "";
                valElem = "";
            }
        }
        if (qName.equals("Ulica")) {
            if (isAddress) {
                person.getAddress().setStreetName(valElem);
                currentElem = "";
                valElem = "";
            }

        }
        if (qName.equals("NrDomu")) {
            if (isAddress) {
                person.getAddress().setStreetNumber(valElem);
                currentElem = "";
                valElem = "";
            }
        }

        if (qName.startsWith("P_")) {
            //String name = currentElem.substring(0);
            String name = currentElem;
            System.out.println(name + " ,value:" + valElem);
            person.addValue(new NameValue(name, valElem));
            valElem = "";
            currentElem = "";
        }

        if (qName.equals("Podmiot2")) {
            isPerson = false;
        }
        if (qName.equals("AdresZamieszkania")) {
            isAddress = false;
        }
        if (qName.equals("PozycjeSzczegolowe")) {
            System.out.println(person.getDataAsString("|",false));
            isData = false;
        }

    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
        String value = new String(chars, start, length);
        if (currentElem.equals("ImiePierwsze")) {
            valElem += value;
        }
        if (currentElem.equals("Nazwisko")) {
            valElem += value;
        }
        if (currentElem.equals("Miejscowosc")) {
            valElem += value;
        }
        if (currentElem.equals("Ulica")) {
            valElem += value;
        }
        if (currentElem.equals("NrDomu")) {
            valElem += value;
        }
        if (currentElem.contains("P_")) {
            valElem += value;
        }
    }

    @Override
    public PersonPIT getPersonFromFile(File file) throws IOException, SAXException, ParserConfigurationException {
        BOMInputStream inputStream = CheckBOM.getStream(file);
        return getPersonFromInputStream(inputStream);
    }

    @Override
    public PersonPIT getPersonFromInputStream(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        person = new PersonPIT();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(inputStream, this);
        return person;
    }
}