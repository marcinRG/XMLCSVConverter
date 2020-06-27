package io.marcinrg.interfaces;

import io.marcinrg.model.Person;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public interface IGetPersonsFromFile<E extends Person> {
    ArrayList<E> getPersonsFromFile(File file) throws IOException, SAXException, ParserConfigurationException;;
    ArrayList<E> getPersonsFromInputSteam(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException;;
 }
