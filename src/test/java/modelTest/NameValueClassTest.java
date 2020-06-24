package modelTest;

import io.marcinrg.model.NameValue;
import io.marcinrg.utils.NumberFormatConversions;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;

public class NameValueClassTest {
    @Test
    public void NameValueTest() {
        NameValue val1 = new NameValue("zero", BigDecimal.ZERO);
        System.out.println(val1);
        Assert.assertEquals("1: toString() should return expected value", val1.toString(),String.format("%s:%s",val1.getName(),val1.getData().toString()));
        String str = "23.45";
        NameValue val2 = new NameValue("value",new BigDecimal(str));
        System.out.println(val2);
        Assert.assertEquals("2: toString() should return expected value", val2.toString(),String.format("%s:%s",val2.getName(),str));
    }
    @Test
    public void ConversionTest() {
        String str = "23.77";
        String strPL = "23,77";
        try {
            System.out.println(String.format("En number: %s pl number: %s",str, NumberFormatConversions.convertNumberEntoPL(str)));
            Assert.assertEquals("Conversion to polish number encoding should work", NumberFormatConversions.convertNumberEntoPL(str),strPL);
            System.out.println(String.format("Pl number: %s en number: %s",strPL, NumberFormatConversions.convertNumberPLtoEN(strPL)));
            Assert.assertEquals("Conversion from polish  to english encoding should work", NumberFormatConversions.convertNumberPLtoEN(strPL),str);
        }
        catch (ParseException e) {
            Assert.fail();
        }
    }
}
