package io.marcinrg.model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PersonZUS extends Person {
    private String PESEL;
    private String disabilityCode;
    private ArrayList<BigDecimal> workTimes;

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
        this.workTimes = new ArrayList<>();
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

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    public String getData(String delimiter) {
        //return String.format("%s%s%s%s", getNameAndSurName(delimiter), delimiter, getPESEL(), getDataAsString(delimiter));
        return getDataAsString(delimiter);
    }

    public String getNames(String delimiter) {
        //return String.format("%s%s%s%s%s%s", "name", delimiter, "surname", delimiter, "PESEL",getDataNamesAsString(delimiter));
        //return String.format("%s%s%s%s", "name", delimiter, "surname", delimiter, "PESEL");
        return getDataNamesAsString(delimiter);
    }

    public void addTime(BigDecimal number) {
        workTimes.add(number);
    }

    public String getTimes() {
        if (workTimes.size() > 1) {
            return workTimes.get(0).toString() + "/" + workTimes.get(1).toString();
        } else {
            return (BigDecimal.ZERO).toString();
        }
    }


}
