package io.marcinrg.model;

import io.marcinrg.interfaces.IGetData;
import io.marcinrg.utils.NumberFormatConversions;

import java.text.ParseException;
import java.util.TreeMap;

public class Person implements IGetData {
    protected String name;
    protected String surName;
    protected TreeMap<String, NameValue> personData;

    public TreeMap<String, NameValue> getPersonData() {
        return personData;
    }

    public Person() {
        this.personData = new TreeMap<>();
        this.name = "";
        this.surName = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void addValue(NameValue element) {
        this.personData.put(element.getName(), element);
    }

    protected String getNameAndSurName(String delimiter) {
        return String.format("%s%s%s", this.name, delimiter, this.surName);
    }

    public String getDataAsString(String delimiter, boolean changeNumbersToPLEncoding) {
        StringBuilder stringBuilder = new StringBuilder();
        if (changeNumbersToPLEncoding) {
            this.personData.values().forEach((elem) -> {
                try {
                    stringBuilder.append(String.format("%s%s", delimiter, NumberFormatConversions.convertNumberEntoPL(elem.getData().toString())));
                } catch (ParseException e) {}
            });
            return stringBuilder.toString();
        } else {
            this.personData.values().forEach((elem) -> stringBuilder.append(String.format("%s%s", delimiter, elem.getData().toString())));
        }
        return stringBuilder.toString();
    }

    protected String getDataNamesAsString(String delimiter) {
        StringBuilder stringBuilder = new StringBuilder();
        this.personData.keySet().forEach((elem) -> stringBuilder.append(String.format("%s%s", delimiter, elem)));
        return stringBuilder.toString();
    }

    @Override
    public String getData(String delimiter) {
        return String.format("%s%s", getNameAndSurName(delimiter), getDataAsString(delimiter, false));
    }

    @Override
    public String getData(String delimiter, boolean changeNumbersToPLEncoding) {
        return String.format("%s%s", getNameAndSurName(delimiter), getDataAsString(delimiter, changeNumbersToPLEncoding));
    }

    public String getNames(String delimiter) {
        return String.format("%s%s%s%s", "name", delimiter, "surname", getDataNamesAsString(delimiter));
    }

}
