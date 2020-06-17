package io.marcinrg.xml;

import io.marcinrg.model.Address;
import io.marcinrg.model.Person;
import io.marcinrg.model.PersonPIT;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PersonPITHandler extends DefaultHandler {
    private PersonPIT person;
    private String currentElem;
    private String valElem;
    private Address address;

    //flags
    private boolean isPerson = false;
    private boolean isAddress = false;
    private boolean isData = false;

    public PersonPITHandler() {
    }

    public Person getPerson() {
        return person;
    }

    private void intialize()
    {
        person = new PersonPIT();
        address = person.getAddress();
        currentElem="";
        valElem="";
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
        }


        if (qName.equals("etd:ImiePierwsze")) {
            if (isPerson) {
                currentElem = qName;
            }
        }
        if (qName.equals("etd:Nazwisko")) {
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

        if (qName.contains("P_")) {
            if (isData) {
                currentElem = qName;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (currentElem.equals("etd:ImiePierwsze")) {
            if (isPerson) {
                person.setName(valElem);
                currentElem = "";
                valElem = "";
            }
        }

        if (qName.equals("etd:Nazwisko")) {
            if (isPerson) {
                person.setSurName(valElem);
                currentElem = "";
                valElem = "";
            }
        }

        if (qName.equals("Miejscowosc")) {
            if (isAddress) {
                address.setCity(valElem);
                currentElem = "";
                valElem = "";
            }
        }
        if (qName.equals("Ulica")) {
            if (isAddress) {
                address.setStreetName(valElem);
                currentElem = "";
                valElem = "";
            }

        }
        if (qName.equals("NrDomu")) {
            if (isAddress) {
                address.setStreetNumber(valElem);
                currentElem = "";
                valElem = "";
            }
        }

        if (qName.contains("P_")) {
            currentElem = "";
        }

        if (qName.equals("Podmiot2")) {
            isPerson = false;
        }
        if (qName.equals("AdresZamieszkania")) {
            isAddress = false;
        }
        if (qName.equals("PozycjeSzczegolowe")) {
            isData = false;
        }

    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
        String value = new String(chars, start, length);
        if (currentElem.equals("etd:ImiePierwsze")) {
            valElem += value;
        }
        if (currentElem.equals("etd:Nazwisko")) {
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
            System.out.println(currentElem);

        }
    }


}