package modelTest;

import io.marcinrg.model.PersonZUS;
import io.marcinrg.xml.PersonZUSHandler;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PersonZUSHandlerTest {
    private String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
            "<KEDU wersja_schematu=\"1\" xmlns=\"http://www.zus.pl/2018/KEDU_5\"\n" +
            "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
            "xsi:schemaLocation=\"http://www.zus.pl/2018/KEDU_5 kedu_5.xsd\">\n" +
            "\n" +
            "<naglowek.KEDU>\n" +
            "<program>\n" +
            "<producent> </producent>\n" +
            "<symbol> </symbol>\n" +
            "<wersja> </wersja>\n" +
            "</program>\n" +
            "<data_utworzenia_KEDU>2020-06-12</data_utworzenia_KEDU>\n" +
            "</naglowek.KEDU>\n" +
            "<ZUSRCA id_dokumentu=\"3\">   \n" +
            "<I>\n" +
            "<p1>\n" +
            "<p1>01</p1>\n" +
            "<p2>2020-05</p2>\n" +
            "</p1>\n" +
            "</I>\n" +
            "<II>\n" +
            "<p1>6131001463</p1><p2>430868511</p2><p3>69861109840</p3>                                           \n" +
            "                                                                                                    \n" +
            "<p6>PPHU&#32;POLSKOPOL</p6>                                                                           \n" +
            "<p7>XEXE</p7><p8>AGELINA</p8><p9>1960-06-11</p9>                                              \n" +
            "</II>\n" +
            "<III id_bloku=\"1\">       \n" +
            "<A>\n" +
            "<p1>XERSES</p1>                                                                               \n" +
            "<p2>WIOLETTA</p2>                                                                                   \n" +
            "<p3>P</p3><p4>86071414587</p4>\n" +
            "</A>\n" +
            "\n" +
            "<B>\n" +
            "<p1>\n" +
            "  <p1>0110</p1>\n" +
            "  <p2>0</p2>\n" +
            "  <p3>0</p3>\n" +
            "</p1>\n" +
            "<p2></p2>\n" +
            "<p3>\n" +
            "    <p1>003</p1>\n" +
            "    <p2>004</p2>\n" +
            "</p3>\n" +
            "<p4>2600.00</p4>         \n" +
            "<p5>2600.00</p5>         \n" +
            "<p6>2600.00</p6>         \n" +
            "<p7>253.76</p7>          \n" +
            "<p8>39.00</p8>           \n" +
            "<p9>63.70</p9>           \n" +
            "<p10>0.00</p10>          \n" +
            "<p11>253.76</p11>        \n" +
            "<p12>169.00</p12>        \n" +
            "<p13>0.00</p13>          \n" +
            "<p14>24.18</p14>         \n" +
            "<p15>0.00</p15>          \n" +
            "<p16>0.00</p16>          \n" +
            "<p17>0.00</p17>          \n" +
            "<p18>0.00</p18>          \n" +
            "<p19>0.00</p19>          \n" +
            "<p20>0.00</p20>          \n" +
            "<p21>0.00</p21>          \n" +
            "<p22>0.00</p22>          \n" +
            "<p23>0.00</p23>          \n" +
            "<p24>0.00</p24>          \n" +
            "<p25>0.00</p25>          \n" +
            "<p26>0.00</p26>          \n" +
            "<p27>0.00</p27>          \n" +
            "<p28>803.40</p28>        \n" +
            "</B>\n" +
            "<C>\n" +
            "<p1>2243.54</p1>         \n" +
            "<p2>0.00</p2>            \n" +
            "<p3>0.00</p3>            \n" +
            "<p4>201.92</p4>          \n" +
            "<p5>0.00</p5>            \n" +
            "</C>\n" +
            "<D>\n" +
            "                         \n" +
            "                         \n" +
            "                         \n" +
            "<p4>0.00</p4>            \n" +
            "</D>\n" +
            "</III>\n" +
            "<V>\n" +
            "<p1>2020-06-12</p1>\n" +
            "</V>\n" +
            "</ZUSRCA>\n" +
            "</KEDU>";

    @Test
    public void getPersonsFromInputStreamMethodTest() {
        try {
            PersonZUSHandler personZUSHandler = new PersonZUSHandler();
            InputStream inputStream = IOUtils.toInputStream(xml, StandardCharsets.UTF_8);
            ArrayList<PersonZUS> personList = personZUSHandler.getPersonsFromInputSteam(inputStream);
            Assert.assertEquals("personList size shoulb be 1", personList.size(), 1);
            PersonZUS personZUS = personList.get(0);
            Assert.assertEquals("Names should be equal", personZUS.getName(), "WIOLETTA");
            Assert.assertEquals("Surnames should be equal", personZUS.getSurName(), "XERSES");
            Assert.assertEquals("values should be equal", personZUS.getPESEL(), "86071414587");
            Assert.assertEquals("values should be equal", personZUS.getDisabilityCode(), "011000");
            Assert.assertEquals("values should be equal", personZUS.getTimes(), "3/4");

        } catch (Exception e) {
            Assert.fail();
        }
    }
}
