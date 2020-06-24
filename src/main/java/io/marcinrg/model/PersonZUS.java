package io.marcinrg.model;

public class PersonZUS extends Person {
    private String PESEL;
    private String disabilityCode;

    public String getDisabilityCode() {
        return disabilityCode;
    }

    public void setDisabilityCode(String disabilityCode) {
        this.disabilityCode = disabilityCode;
    }

    public PersonZUS() {
        super();
        this.PESEL = "";
        this.disabilityCode = "";
    }

    public PersonZUS(String name, String surName) {
        this();
        this.setName(name);
        this.setSurName(surName);
    }

    public PersonZUS(String name, String surName, String PESEL) {
        this(name, surName);
        this.setPESEL(PESEL);
    }

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String  PESEL) {
        this.PESEL = PESEL;
    }

    public String getData(String delimiter) {
        return String.format("%s%s%s%s", getNameAndSurName(delimiter), delimiter, getPESEL() , getDataAsString(delimiter));
    }

    public String getNames(String delimiter) {
        //return String.format("%s%s%s%s%s%s", "name", delimiter, "surname", delimiter, "PESEL",getDataNamesAsString(delimiter));
        return String.format("%s%s%s%s", "name", delimiter, "surname", delimiter, "PESEL");
    }

}
