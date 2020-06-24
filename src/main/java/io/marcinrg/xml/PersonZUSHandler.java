package io.marcinrg.xml;

import io.marcinrg.model.NameValue;
import io.marcinrg.model.Person;
import io.marcinrg.model.PersonZUS;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;

public class PersonZUSHandler extends DefaultHandler {
    private PersonZUS person;
    private String currentElem;
    private String valElem;

    public int personCount = 0;
    private final String header = "row";
    private boolean isDocument;
    private boolean isPerson;
    private boolean isPersonalData;
    private boolean isData1st;
    private boolean isData2nd;
    private boolean isData3rd;
    private boolean isDisabilityWork;
    private boolean isDisabilityData;
    private boolean isWorkTime;

    public PersonZUSHandler() {
    }

    public Person getPerson() {
        return person;
    }

    private void intialize() {
        currentElem = "";
        valElem = "";
        personCount = 0;
    }

    @Override
    public void startDocument() throws SAXException {
        intialize();
    }

    @Override
    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes)
            throws SAXException {
        if (qName.equals("ZUSRCA")) {
            isDocument = true;
        }

        if (isDocument && qName.equals("III")) {
            isPerson = true;
            personCount = personCount + 1;
            person = new PersonZUS();
        }

        if (isPerson && qName.equals("A")) {
            isPersonalData = true;
        }

        if (isPerson && qName.equals("B")) {
            isData1st = true;
        }

        if (isPerson && qName.equals("C")) {
            isData2nd = true;
        }

        if (isPerson && qName.equals("D")) {
            isData3rd = true;
        }


        if (isData1st && qName.startsWith("p")) {
            if (qName.equals("p1")) {
                currentElem = "disability";
                valElem = "";
                isDisabilityData = true;
                isDisabilityWork = true;
            }

            if (qName.equals("p2")) {
                isDisabilityData = false;
                isDisabilityWork = true;
            }

            if (qName.equals("p2")) {
                isWorkTime = true;
                isDisabilityWork = true;
            }

            if (!isDisabilityWork) {
                currentElem = header + 0 + qName;
                valElem = "";
            }

        }

        if (isData2nd && qName.startsWith("p")) {
            currentElem = header + 1 + qName;
            valElem = "";
        }

        if (isData3rd && qName.startsWith("p")) {
            currentElem = header + 2 + qName;
            valElem = "";
        }


        if (isPersonalData && qName.equals("p1")) {
            currentElem = qName;
            valElem = "";
        }

        if (isPersonalData && qName.equals("p2")) {
            currentElem = qName;
            valElem = "";
        }

        if (isPersonalData && qName.equals("p4")) {
            currentElem = qName;
            valElem = "";
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("ZUSRCA")) {
            System.out.println("document End");
            if (isDocument) {
                System.out.println(personCount);

            }
            isDocument = false;
        }

        if (isPerson && qName.equals("III")) {
            System.out.println("person end");
            //System.out.println(person.getNames("|"));
            //System.out.println(person.getData("|"));
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

        if (isPersonalData && qName.equals("p1")) {
            System.out.println(valElem);
            person.setName(valElem);
            valElem = "";
        }

        if (isPersonalData && qName.equals("p2")) {
            System.out.println(valElem);
            person.setSurName(valElem);
            valElem = "";
        }

        if (isPersonalData && qName.equals("p4")) {
            System.out.println(valElem);
            person.setPESEL(valElem);
            valElem = "";
        }

        if ((isData1st || isData2nd || isData3rd) && qName.startsWith("p")) {
            System.out.println("cur:" + currentElem);
            System.out.println("val:" + valElem);
            //person.addValue(new NameValue(currentElem, new BigDecimal(valElem)));
            valElem = "";
        }


    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
        String value = new String(chars, start, length);
        if (isPersonalData && currentElem.equals("p1")) {
            valElem += value;
        }
        if (isPersonalData && currentElem.equals("p2")) {
            valElem += value;
        }
        if (isPersonalData && currentElem.equals("p4")) {
            valElem += value;
        }

        if ((isData1st || isData2nd || isData3rd) && currentElem.startsWith(header)) {
            valElem += value;
        }

    }

}