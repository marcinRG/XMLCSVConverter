package io.marcinrg.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class NumberFormatConversions {
    public static final Locale locPL = new Locale("pl", "PL");
    public static final Locale locEn = new Locale("en", "US");
    public static final DecimalFormat nfPL = (DecimalFormat) NumberFormat.getNumberInstance(locPL);
    public static final DecimalFormat nfEn = (DecimalFormat) NumberFormat.getNumberInstance(locEn);


    public static String convertNumberPLtoEN(String in) throws ParseException
    {
        String s = in = in.replace(" ", "");
        s = nfEn.format(nfPL.parse(in));
        return s.replaceAll(""+nfEn.getDecimalFormatSymbols().getGroupingSeparator(),"");//nfEn.format(nfPL.parse(in));
    }

    public static String convertNumberEntoPL(String in) throws ParseException
    {
        String s = in = in.replace(" ", "");
        s=nfPL.format(nfEn.parse(in));
        return s.replaceAll(""+nfPL.getDecimalFormatSymbols().getGroupingSeparator(),"");
    }
}
