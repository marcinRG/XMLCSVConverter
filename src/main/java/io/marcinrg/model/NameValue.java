package io.marcinrg.model;

import java.math.BigDecimal;

public class NameValue {
    private String name;
    private BigDecimal data;

    public NameValue(String name) {
        this.name = name;
        this.data = BigDecimal.ZERO;
    }

    public NameValue(String name, BigDecimal data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getData() {
        return data;
    }

    public void setData(BigDecimal data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return this.name + ':' + this.data.toString();
    }

}
