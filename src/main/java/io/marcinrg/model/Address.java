package io.marcinrg.model;

import io.marcinrg.interfaces.IGetData;

public class Address implements IGetData {
    private String city;
    private String streetName;
    private String streetNumber;

    public Address(String city, String streetName, String streetNumber) {
        this.city = city;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
    }

    public Address()
    {
        this.city ="";
        this.streetName ="";
        this.streetNumber ="";
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    @Override
    public String getData() {
        return city + " " + streetName + " " + streetNumber;
    }
}
