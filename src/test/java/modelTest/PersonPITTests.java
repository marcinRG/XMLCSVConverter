package modelTest;

import io.marcinrg.model.Address;
import org.junit.Assert;
import org.junit.Test;

public class PersonPITTests {
    @Test
    public void classAddressTest() {
        Address address = new Address();
        Assert.assertNotNull("Object should not be null", address);
        Assert.assertNotNull("Field City should not be null", address.getCity());
        Assert.assertNotNull("Field StreetName not be null", address.getStreetName());
        Assert.assertNotNull("Field StreetNumber should not be null", address.getStreetNumber());
        String city = "Wrocław";
        address.setCity(city);
        Assert.assertEquals("Field City should return expected value", address.getCity(), city);
        String streetName = "Sienkiewicza";
        String streetNumber = "50/Z/12";
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        Assert.assertEquals("Field StreetName should return expected value", address.getStreetName(), streetName);
        Assert.assertEquals("Field StreetNumber should return expected value", address.getStreetNumber(), streetNumber);
        Assert.assertEquals("Method getNames should return expected value", address.getNames("|"), "|city|street name|street number");
        Assert.assertEquals("Method getNames should return expected value", address.getData("|"), "|"+city+"|"+streetName+"|"+streetNumber);
        System.out.println(address.getNames("|"));
        System.out.println(address.getData("|"));
    }
}
