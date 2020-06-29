package io.marcinrg.interfaces;

public interface IGetData {
    String getData(String delimiter);
    String getData(String delimiter, boolean changeNumbersToPLEncoding);
    String getNames(String delimiter);
}
