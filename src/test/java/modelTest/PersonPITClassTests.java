package modelTest;

import io.marcinrg.model.Address;
import io.marcinrg.model.NameValue;
import io.marcinrg.model.PersonPIT;
import org.junit.Assert;
import org.junit.Test;

public class PersonPITClassTests {
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
    @Test
    public void PersonPITTest() {
        PersonPIT personPIT = new PersonPIT();
        var name = "Name";
        var surName = "Sur Name";
        Assert.assertNotNull("Object should not be null", personPIT);
        Assert.assertNotNull("Field address should not be null", personPIT.getAddress());
        Assert.assertNotNull("Field name should not be null", personPIT.getName());
        Assert.assertNotNull("Field surName should not be null", personPIT.getSurName());
        Assert.assertNotNull("Field personData should not be null", personPIT.getPersonData());

        personPIT.setName(name);
        personPIT.setSurName(surName);

        Address address = personPIT.getAddress();
        String city = "Wrocław";
        address.setCity(city);
        String streetName = "Sienkiewicza";
        String streetNumber = "50/Z/12";
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);

        personPIT.getPersonData().put("value", new NameValue("Value","67"));
        Assert.assertEquals("results should be the same",personPIT.getNames("|"), "name|surname|city|street name|street number|value");
        Assert.assertEquals("results should be the same",personPIT.getData("|"), "Name|Sur Name|Wrocław|Sienkiewicza|50/Z/12|67");

    }
}
