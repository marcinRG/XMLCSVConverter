package io.marcinrg.collections;


import io.marcinrg.model.Person;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;

public class PersonCollection {
    private final ArrayList<Person> personsList;
    private DefaultHandler handler;

    public DefaultHandler getHandler() {
        return handler;
    }

    public void setHandler(DefaultHandler handler) {
        this.handler = handler;
    }

    public PersonCollection() {
        personsList = new ArrayList<>();
    }

    public void getPersonsFromXMLFiles(FileCollection files) {
        if (files.getFileList().size() > 0 && handler != null) {
            try {
                personsList.clear();
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                for (File f : files.getFileList()) {
                    File inputFile = f;
                    InputStream inputStream = new FileInputStream(inputFile);
                    Reader reader = new InputStreamReader(inputStream, "UTF-8");
                    InputSource is = new InputSource(reader);
                    is.setEncoding("UTF-8");
                    saxParser.parse(is, handler);
                }


            } catch (ParserConfigurationException | SAXException | IOException e) {
            }
        }
    }
}