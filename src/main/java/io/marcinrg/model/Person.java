package io.marcinrg.model;


import io.marcinrg.interfaces.IGetData;

import java.util.TreeMap;

public class Person implements IGetData {
    protected String name;
    protected String surName;
    protected TreeMap<String,NameValue> personData;

    public TreeMap<String, NameValue> getPersonData() {
        return personData;
    }

    public void setPersonData(TreeMap<String, NameValue> personData) {
        this.personData = personData;
    }

    public Person() {
        this.name = "";
        this.surName = "";
    }

    public Person(String name, String surName) {
        this.name = name;
        this.surName = surName;
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

    @Override
    public String getData() {
        return "Person";
    }
}
