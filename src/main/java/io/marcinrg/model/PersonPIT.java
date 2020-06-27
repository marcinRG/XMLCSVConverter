package io.marcinrg.model;

public class PersonPIT extends Person {

    private Address address;

    @Override
    public String getData(String delimiter) {
        return String.format("%s%s%s%s", getNameAndSurName(delimiter), address.getData(delimiter), getDataAsString(delimiter));

    }

    @Override
    public String getNames(String delimiter) {
        return String.format("%s%s%s%s%s", "name", delimiter, "surname", address.getNames(delimiter), getDataNamesAsString(delimiter));
    }

    public PersonPIT() {
        super();
        address = new Address();
    }

    public PersonPIT(String name, String surName) {
        this();
        this.name = name;
        this.surName = surName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
