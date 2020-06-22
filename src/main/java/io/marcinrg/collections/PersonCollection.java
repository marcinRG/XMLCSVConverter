package io.marcinrg.collections;


import io.marcinrg.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PersonCollection {
    private final ObservableList<Person> personsList;
    private DefaultHandler handler;

    public ObservableList<Person> getPersonsList() {
        return personsList;
    }

    public void clear() {
        personsList.clear();
    }

    public DefaultHandler getHandler() {
        return handler;
    }

    public void setHandler(DefaultHandler handler) {
        this.handler = handler;
    }

    public PersonCollection() {
        personsList = FXCollections.observableArrayList();
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
                    Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                    InputSource is = new InputSource(reader);
                    is.setEncoding("UTF-8");
                    saxParser.parse(is, handler);
                }


            } catch (ParserConfigurationException | SAXException | IOException e) {
                System.out.println("Errors ocurred");
            }
        }
    }
}
