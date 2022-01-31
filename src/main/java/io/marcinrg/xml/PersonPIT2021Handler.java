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

public class PersonPIT2021Handler extends DefaultHandler implements IGetPersonFromFile<PersonPIT> {

    private PersonPIT person;
    private String currentElem;
    private String valElem;

    //flags
    private boolean isPerson = false;
    private boolean isAddress = false;
    private boolean isData = false;

    public PersonPIT2021Handler() {
    }

    private void intialize() {
        person = new PersonPIT();
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
        if (qName.equals("n1:Podmiot2")) {
            isPerson = true;
        }
        if (qName.equals("n1:AdresZamieszkania")) {
            if (isPerson) isAddress = true;
            person.getAddress().setStreetName("puste");
        }


        if (qName.equals("n3:ImiePierwsze")) {
            if (isPerson) {
                currentElem = qName;
            }
        }
        if (qName.equals("n3:Nazwisko")) {
            if (isPerson) {
                currentElem = qName;
            }
        }

        if (qName.equals("n1:Miejscowosc")) {
            if (isAddress) currentElem = qName;
        }
        if (qName.equals("n1:Ulica")) {
            if (isAddress) currentElem = qName;
        }
        if (qName.equals("n1:NrDomu")) {
            if (isAddress) currentElem = qName;
        }

        if (qName.equals("n1:PozycjeSzczegolowe")) {
            isData = true;
        }

        if (qName.startsWith("n1:P_")) {
            if (isData) {
                currentElem = qName;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (currentElem.equals("n3:ImiePierwsze")) {
            if (isPerson) {
                person.setName(valElem);
                currentElem = "";
                valElem = "";
            }
        }

        if (qName.equals("n3:Nazwisko")) {
            if (isPerson) {
                person.setSurName(valElem);
                currentElem = "";
                valElem = "";
            }
        }

        if (qName.equals("n1:Miejscowosc")) {
            if (isAddress) {
                person.getAddress().setCity(valElem);
                currentElem = "";
                valElem = "";
            }
        }
        if (qName.equals("n1:Ulica")) {
            if (isAddress) {
                person.getAddress().setStreetName(valElem);
                currentElem = "";
                valElem = "";
            }

        }
        if (qName.equals("n1:NrDomu")) {
            if (isAddress) {
                person.getAddress().setStreetNumber(valElem);
                currentElem = "";
                valElem = "";
            }
        }

        if (qName.startsWith("n1:P_")) {
            String name = currentElem.substring(3);
            person.addValue(new NameValue(name, valElem));
            valElem = "";
            currentElem = "";
        }

        if (qName.equals("n1:Podmiot2")) {
            isPerson = false;
        }
        if (qName.equals("n1:AdresZamieszkania")) {
            isAddress = false;
        }
        if (qName.equals("n1:PozycjeSzczegolowe")) {
            isData = false;
        }

    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
        String value = new String(chars, start, length);
        if (currentElem.equals("n3:ImiePierwsze")) {
            valElem += value;
        }
        if (currentElem.equals("n3:Nazwisko")) {
            valElem += value;
        }
        if (currentElem.equals("n1:Miejscowosc")) {
            valElem += value;
        }
        if (currentElem.equals("n1:Ulica")) {
            valElem += value;
        }
        if (currentElem.equals("n1:NrDomu")) {
            valElem += value;
        }
        if (currentElem.contains("n1:P_")) {
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