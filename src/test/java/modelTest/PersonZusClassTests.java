package modelTest;

import io.marcinrg.model.Address;
import io.marcinrg.model.NameValue;
import io.marcinrg.model.PersonPIT;
import io.marcinrg.model.PersonZUS;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class PersonZusClassTests {

    @Test
    public void PersonZUSClassTest() {
        PersonZUS personZUS = new PersonZUS();
        var name = "Name";
        var surName = "Sur Name";
        Assert.assertNotNull("Object should not be null", personZUS);
        Assert.assertNotNull("Field name should not be null", personZUS.getName());
        Assert.assertNotNull("Field surName should not be null", personZUS.getSurName());
        Assert.assertNotNull("Field personData should not be null", personZUS.getPersonData());
        Assert.assertNotNull("Field personData should not be null", personZUS.getPESEL());
        Assert.assertNotNull("Field disabilityCode should not be null", personZUS.getDisabilityCode());
        Assert.assertNotNull("Field workTimes should not be null", personZUS.getWorkTimes());

        personZUS.setName(name);
        personZUS.setSurName(surName);

        personZUS.addTime(BigDecimal.ONE);
        personZUS.addTime(BigDecimal.TEN);
        personZUS.setDisabilityCode("0110000");
        personZUS.setPESEL("8812881250");

        personZUS.getPersonData().put("value", new NameValue("Value","67"));
        personZUS.getPersonData().put("value2", new NameValue("val2", new BigDecimal("345.22")));
        Assert.assertEquals("results should be the same",personZUS.getNames("|"), "name|surname|PESEL|Disability Code|Work time|value|value2");
        Assert.assertEquals("results should be the same",personZUS.getData("|"), "Name|Sur Name|8812881250|0110000|1/10|67|345.22");
    }
}
