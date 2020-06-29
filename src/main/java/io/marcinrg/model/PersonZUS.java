package io.marcinrg.model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PersonZUS extends Person {
    private String PESEL;
    private String disabilityCode;

    public ArrayList<BigDecimal> getWorkTimes() {
        return workTimes;
    }

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

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    @Override
    public String getData(String delimiter, boolean changeNumbersToPLEncoding) {
        return String.format("%s%s%s%s%s%s%s%s", getNameAndSurName(delimiter), delimiter, getPESEL(), delimiter,
                getDisabilityCode(), delimiter, getTimes(), getDataAsString(delimiter, changeNumbersToPLEncoding));
    }

    @Override
    public String getData(String delimiter) {
        return getData(delimiter, false);
    }

    @Override
    public String getNames(String delimiter) {
        return String.format("%s%s%s%s%s%s%s%s%s%s", "name", delimiter, "surname", delimiter, "PESEL", delimiter,
                "Disability Code", delimiter, "Work time", getDataNamesAsString(delimiter));
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
