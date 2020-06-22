package io.marcinrg.model;

public class PersonZUS extends Person {

    public PersonZUS() {
        super();
    }

    public PersonZUS(String name, String surName) {
        this();
        this.name = name;
        this.surName= surName;
    }

    @Override
    public String getData() {
        return "Person ZUS";
    }
}
