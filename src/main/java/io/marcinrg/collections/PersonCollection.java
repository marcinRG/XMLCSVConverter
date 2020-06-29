package io.marcinrg.collections;


import io.marcinrg.model.FileWithPOM;
import io.marcinrg.model.Person;
import io.marcinrg.model.PersonPIT;
import io.marcinrg.xml.PersonPIT2019Handler;
import io.marcinrg.xml.PersonZUSHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;


public class PersonCollection {
    private final ObservableList<Person> personsList;

    public ObservableList<Person> getPersonsList() {
        return personsList;
    }

    public void clear() {
        personsList.clear();
    }

    public PersonCollection() {
        personsList = FXCollections.observableArrayList();
    }

    public void getPersonsFromPITFiles(FileCollection fileCollection) {
        if (fileCollection.getFileList().size() > 0) {
            personsList.clear();
            PersonPIT2019Handler personPIT2019Handler = new PersonPIT2019Handler();
            for (FileWithPOM element : fileCollection.getFileList()) {
                try {
                    PersonPIT personPIT = personPIT2019Handler.getPersonFromFile(element.getFile());
                    personsList.add(personPIT);
                } catch
                (IOException | SAXException | ParserConfigurationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getPersonsFromZUSXMLFile(FileCollection fileCollection) {
        if (fileCollection.getFileList().size() == 1) {
            try {
                personsList.clear();
                PersonZUSHandler personZUSHandler = new PersonZUSHandler();
                personsList.addAll(personZUSHandler.getPersonsFromFile(fileCollection.getFileList().get(0).getFile()));

            } catch (IOException | ParserConfigurationException | SAXException e) {
                e.printStackTrace();
            }
        }
    }
}
