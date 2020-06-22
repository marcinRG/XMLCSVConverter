package io.marcinrg.model;

public class PersonPIT extends Person {
    private String PESEL;
    private Address address;

    public PersonPIT() {
        super();
        this.address = new Address();
        this.PESEL = "";
    }

    @Override
    public String getData() {
        return "PersonPIT";
    }

    public PersonPIT(String name, String surName) {
        this();
        this.setName(name);
        this.setSurName(surName);
    }

    public PersonPIT(String name, String surName, String PESEL) {
        this(name, surName);
        this.setPESEL(PESEL);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String  PESEL) {
        this.PESEL = PESEL;
    }
}
