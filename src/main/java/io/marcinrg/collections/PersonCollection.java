package io.marcinrg.collections;


import io.marcinrg.model.FileWithPOM;
import io.marcinrg.model.Person;
import io.marcinrg.model.PersonPIT;
import io.marcinrg.xml.PersonPIT2021Handler;
import io.marcinrg.xml.PersonPIT2022Handler;
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

    public void getPersonsFromPIT2021Files(FileCollection fileCollection) {
        if (fileCollection.getFileList().size() > 0) {
            personsList.clear();
            PersonPIT2021Handler personPIT2021Handler = new PersonPIT2021Handler();
            for (FileWithPOM element : fileCollection.getFileList()) {
                try {
                    PersonPIT personPIT = personPIT2021Handler.getPersonFromFile(element.getFile());
                    personsList.add(personPIT);
                } catch
                (IOException | SAXException | ParserConfigurationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getPersonsFromPIT2022Files(FileCollection fileCollection) {
        if (fileCollection.getFileList().size() > 0) {
            personsList.clear();
            PersonPIT2022Handler personPIT2022Handler = new PersonPIT2022Handler();
            for (FileWithPOM element : fileCollection.getFileList()) {
                try {
                    PersonPIT personPIT = personPIT2022Handler.getPersonFromFile(element.getFile());
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
