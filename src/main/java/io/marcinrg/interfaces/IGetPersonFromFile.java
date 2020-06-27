package io.marcinrg.interfaces;

import io.marcinrg.model.Person;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface IGetPersonFromFile<E extends Person> {
    E getPersonFromFile(File file) throws IOException, SAXException, ParserConfigurationException;
    E getPersonFromInputStream(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException;
}
