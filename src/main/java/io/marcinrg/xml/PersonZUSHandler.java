package io.marcinrg.xml;

import io.marcinrg.model.Person;
import io.marcinrg.model.PersonZUS;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PersonZUSHandler extends DefaultHandler {
    private PersonZUS person;
    private String currentElem;
    private String valElem;

    public int personCount = 0;
    private final String header = "row";
    private final String disabilityLabel = "disabilityCode";
    private boolean isDocument;
    private boolean isPerson;
    private boolean isPersonalData;
    private boolean isData1st;
    private boolean isData2nd;
    private boolean isData3rd;
    private boolean isData;
    private int level = 0;

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
        level = 0;

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
        }

        if (isPerson && qName.equals("A")) {
            isPersonalData = true;
        }

        if (isPerson && qName.equals("B")) {
            isData1st = true;
            level = 0;
        }

        if (isPerson && qName.equals("C")) {
            isData2nd = true;
        }

        if (isPerson && qName.equals("D")) {
            isData3rd = true;
        }

        if (isData1st && (qName.equals("p1") || qName.equals("p2") || qName.equals("p3"))) {
            isData= false;
            level += 1;
        }

        if (isData1st && !(qName.equals("p1") || qName.equals("p2") || qName.equals("p3"))) {
            isData = true;
        }


    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("ZUSRCA")) {
            isDocument = false;
        }

        if (isPerson && qName.equals("III")) {
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
            System.out.println(qName + " : " + valElem);
        }

        if (isData1st && (qName.equals("p1") || qName.equals("p2") || qName.equals("p3"))) {
            level -= 1;
        }

        if (isData1st && isData) {
            System.out.println("Data:" + qName + " : " + valElem);
            isData = false;
        }

        valElem = "";


    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
        String value = new String(chars, start, length);
        valElem += value;
    }

}