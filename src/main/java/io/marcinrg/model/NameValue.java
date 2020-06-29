package io.marcinrg.model;

import java.math.BigDecimal;

public class NameValue {
    private String name;
    private BigDecimal data;

    public NameValue(String name, String valueAsString) {
        this.name = name;
        if (valueAsString.equals("") || valueAsString.equals(" ")) {
            data = BigDecimal.ZERO;
        }
        data = new BigDecimal(valueAsString);
    }

    public NameValue(String name, BigDecimal data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getData() {
        return data;
    }

    @Override
    public String toString() {
        return this.name + ':' + this.data.toString();
    }

}
