package io.marcinrg.model;

public class PersonPIT extends Person {

    private Address address;

    @Override
    public String getData(String delimiter,boolean changeNumbersToPLEncoding) {
        return String.format("%s%s%s", getNameAndSurName(delimiter), address.getData(delimiter), getDataAsString(delimiter,changeNumbersToPLEncoding));
    }

    @Override
    public String getData(String delimiter) {
        return getData(delimiter, false);
    }

    @Override
    public String getNames(String delimiter) {
        return String.format("%s%s%s%s%s", "name", delimiter, "surname", address.getNames(delimiter), getDataNamesAsString(delimiter));
    }

    public PersonPIT() {
        super();
        address = new Address();
    }

    public Address getAddress() {
        return address;
    }

}
