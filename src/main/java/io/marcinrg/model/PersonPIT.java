package io.marcinrg.model;

import java.math.BigDecimal;

public class PersonPIT extends Person {

    private Address address;

    @Override
    public String getData(String delimiter, boolean changeNumbersToPLEncoding) {
        return String.format("%s%s%s", getNameAndSurName(delimiter), address.getData(delimiter), getDataAsString(delimiter, changeNumbersToPLEncoding));
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
        this.initializeData();
    }

    private void initializeData() {
        String pre = "P_";
        for (int i = 1; i <= 100; i++) {
            this.addValue(new NameValue(pre + i, BigDecimal.ZERO));
        }
    }

    public Address getAddress() {
        return address;
    }

}
