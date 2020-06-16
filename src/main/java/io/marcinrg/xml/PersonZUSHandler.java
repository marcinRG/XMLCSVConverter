package io.marcinrg.xml;

import io.marcinrg.model.Person;
import io.marcinrg.model.PersonZUS;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PersonZUSHandler extends DefaultHandler {
    private Person person;
    private String currentElem;
    private String valElem;

    public PersonZUSHandler() {
    }

    public Person getPerson() {
        return person;
    }

    private void intialize()
    {
        this.person = new PersonZUS();
        currentElem="";
        valElem="";
    }

    @Override
    public void startDocument() throws SAXException
    {
        intialize();
    }

    @Override
    public void startElement(String uri,String localName, String qName, org.xml.sax.Attributes attributes)
            throws SAXException
    {
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException
    {
    }

}