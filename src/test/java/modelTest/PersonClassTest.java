package modelTest;

import io.marcinrg.model.NameValue;
import io.marcinrg.model.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class PersonClassTest {
    private Person person;

    @Before
    public void initialize() {
        System.out.println("Initialisation");
        person = new Person();
    }

    @Test
    public void ClassConstructorTests() {
        System.out.println("Object and all fields should be initialized");
        Assert.assertNotNull("Object should not be null", person);
        Assert.assertNotNull("field name should be initialised", person.getName());
        Assert.assertNotNull("field surName should be initialised", person.getSurName());
        Assert.assertNotNull("field personData should be initialised", person.getPersonData());
    }

    @Test
    public void ClassGettersTests() {
        System.out.println("All object getters should return expected values");
        String name = "John";
        String surName = "Doe";
        String delimiter = "|";
        person.setName(name);
        person.setSurName(surName);
        Assert.assertEquals("Field name should return same value", name, person.getName());
        Assert.assertEquals("Field surName should return same value", surName, person.getSurName());
        System.out.println(person.getData(delimiter));
        Assert.assertEquals("getData should return expected value", String.format("%s%s%s", name, delimiter, surName), person.getData(delimiter));
    }

    @Test
    public void ClassFieldPersonDataTest() {
        String name = "Timmy";
        String surName = "Johnson";
        String delimiter = "|";
        BigDecimal bd1 = BigDecimal.ZERO;
        NameValue val1 = new NameValue("arg_1", bd1);
        String str = "23.45";
        BigDecimal bd2 = new BigDecimal(str);
        NameValue val2 = new NameValue("arg_2", bd2);

        String str3 = "188.45";
        BigDecimal bd3 = new BigDecimal(str3);
        NameValue val3 = new NameValue("arg_3", bd3);

        String str2 = "88.45";
        BigDecimal bd4 = new BigDecimal(str2);
        NameValue val4 = new NameValue("arg_4", bd4);

        person.setName(name);
        person.setSurName(surName);

        person.addValue(val1);
        person.addValue(val2);
        person.addValue(val3);
        person.addValue(val4);

        Person person1 = new Person();
        person1.setName(name);
        person1.setSurName(surName);
        person1.addValue(val1);
        person1.addValue(val2);
        person1.addValue(val3);
        person1.addValue(val4);


        System.out.println(person.getNames(delimiter));
        System.out.println(person.getData(delimiter));
        System.out.println(person1.getData(delimiter));

        String expectedValue = new String(name + delimiter + surName + delimiter + bd1.toString() + delimiter
                + bd2.toString() + delimiter + bd3.toString() + delimiter + bd4.toString());
        System.out.println(expectedValue);
        Assert.assertEquals("getData() should return expected value",person.getData(delimiter),expectedValue);
    }
}
