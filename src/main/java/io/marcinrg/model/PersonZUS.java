package io.marcinrg.model;

public class PersonZUS extends Person {

    public PersonZUS() {
        super();
    }

    public PersonZUS(String name, String surName) {
        this();
        this.setName(name);
        this.setSurName(surName);
    }

    @Override
    public String getData() {
        return "Person ZUS";
    }
}
