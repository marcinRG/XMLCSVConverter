package modelTest;

import io.marcinrg.model.PersonPIT;
import io.marcinrg.xml.PersonPIT2019Handler;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PersonPIT2019HandlerTest {
    private String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<Deklaracja xsi:schemaLocation=\"http://crd.gov.pl/wzor/2019/12/19/8981/schemat.xsd\" xmlns=\"http://crd.gov.pl/wzor/2019/12/19/8981/\" xmlns:etd=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2018/08/24/eD/DefinicjeTypy/\" xmlns:zr=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2019/12/13/eD/PITR/\" xmlns:zzu=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2018/08/24/eD/ORDZU/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "<Naglowek>\n" +
            "<KodFormularza kodSystemowy=\"PIT-11 (25)\" kodPodatku=\"PIT\" rodzajZobowiazania=\"Z\" wersjaSchemy=\"1-0E\">PIT-11</KodFormularza>\n" +
            "<WariantFormularza>25</WariantFormularza>\n" +
            "<CelZlozenia poz=\"P_7\">2</CelZlozenia>\n" +
            "<Rok>2019</Rok>\n" +
            "<KodUrzedu>0211</KodUrzedu>\n" +
            "</Naglowek>\n" +
            "<Podmiot1 rola=\"Płatnik/Składający\">\n" +
            "<OsobaFizyczna>\n" +
            "<etd:NIP>9001003040</etd:NIP>\n" +
            "<etd:ImiePierwsze>Xerox</etd:ImiePierwsze>\n" +
            "<etd:Nazwisko>Anonymous</etd:Nazwisko>\n" +
            "<etd:DataUrodzenia>1939-09-01</etd:DataUrodzenia>\n" +
            "</OsobaFizyczna>\n" +
            "</Podmiot1>\n" +
            "<Podmiot2 rola=\"Podatnik\">\n" +
            "<OsobaFizyczna>\n" +
            "<etd:PESEL>989901020304</etd:PESEL>\n" +
            "<etd:ImiePierwsze>Aneta</etd:ImiePierwsze>\n" +
            "<etd:Nazwisko>Doe</etd:Nazwisko>\n" +
            "<etd:DataUrodzenia>1982-12-05</etd:DataUrodzenia>\n" +
            "</OsobaFizyczna>\n" +
            "<AdresZamieszkania rodzajAdresu=\"RAD\">\n" +
            "<KodKraju poz=\"P_19A\">PL</KodKraju>\n" +
            "<Wojewodztwo>dolnośląskie</Wojewodztwo>\n" +
            "<Powiat>Warszawa</Powiat>\n" +
            "<Gmina>Wrocław</Gmina>\n" +
            "<Ulica poz=\"P_23\">Dubois</Ulica>\n" +
            "<NrDomu poz=\"P_24\">22</NrDomu>\n" +
            "<NrLokalu poz=\"P_25\">1</NrLokalu>\n" +
            "<Miejscowosc poz=\"P_26\">Jelenia Góra</Miejscowosc>\n" +
            "<KodPocztowy poz=\"P_27\">59-800</KodPocztowy>\n" +
            "</AdresZamieszkania>\n" +
            "</Podmiot2>\n" +
            "<PozycjeSzczegolowe>\n" +
            "<P_5>1</P_5>\n" +
            "<P_11>1</P_11>\n" +
            "<P_28>1</P_28>\n" +
            "<P_29>21585.19</P_29>\n" +
            "<P_30>1751.25</P_30>\n" +
            "<P_31>19833.94</P_31>\n" +
            "<P_33>1081</P_33>\n" +
            "<P_43>0</P_43>\n" +
            "<P_44>0</P_44>\n" +
            "<P_45>0</P_45>\n" +
            "<P_46>0</P_46>\n" +
            "<P_47>0</P_47>\n" +
            "<P_49>0</P_49>\n" +
            "<P_50>0</P_50>\n" +
            "<P_51>0</P_51>\n" +
            "<P_53>0</P_53>\n" +
            "<P_54>0</P_54>\n" +
            "<P_55>0</P_55>\n" +
            "<P_57>0</P_57>\n" +
            "<P_58>0</P_58>\n" +
            "<P_59>0</P_59>\n" +
            "<P_60>0</P_60>\n" +
            "<P_61>0</P_61>\n" +
            "<P_64>379.72</P_64>\n" +
            "<P_65>0</P_65>\n" +
            "<P_66>379.72</P_66>\n" +
            "<P_68>0</P_68>\n" +
            "<P_69>2863.15</P_69>\n" +
            "<P_72>1450.98</P_72>\n" +
            "<P_89>2</P_89>\n" +
            "</PozycjeSzczegolowe>\n" +
            "<Pouczenie>1</Pouczenie>\n" +
            "<Zalaczniki>\n" +
            "<zzu:Zalacznik_ORD-ZU>\n" +
            "<zzu:Naglowek>\n" +
            "<zzu:KodFormularza kodSystemowy=\"ORD-ZU (3)\" wersjaSchemy=\"2-0E\">ORD-ZU</zzu:KodFormularza>\n" +
            "<zzu:WariantFormularza>3</zzu:WariantFormularza>\n" +
            "</zzu:Naglowek>\n" +
            "<zzu:PozycjeSzczegolowe>\n" +
            "<zzu:P_13>Błędnie wygenerowane PIT 11, bład w pozycji 65</zzu:P_13>\n" +
            "</zzu:PozycjeSzczegolowe>\n" +
            "</zzu:Zalacznik_ORD-ZU>\n" +
            "</Zalaczniki><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"Signature-1039829971\">\n" +
            "\t<ds:SignedInfo>\n" +
            "\t\t<ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>\n" +
            "\t\t<ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/>\n" +
            "\t\t<ds:Reference URI=\"\">\n" +
            "\t\t\t<ds:Transforms>\n" +
            "\t\t\t\t<ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/>\n" +
            "\t\t\t\t<ds:Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>\n" +
            "\t\t\t</ds:Transforms>\n" +
            "\t\t\t<ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>\n" +
            "\t\t\t<ds:DigestValue>NEpOikgVI1BAlKKz9shBQ/mxYY/lMGDqEf+lqu+KbdI=</ds:DigestValue>\n" +
            "\t\t</ds:Reference>\n" +
            "\t\t<ds:Reference Type=\"http://uri.etsi.org/01903#SignedProperties\" URI=\"#SignedProperties-1862011922\">\n" +
            "\t\t\t<ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>\n" +
            "\t\t\t<ds:DigestValue>CaZzqpNwas6wg2vYBcX1xfoiemj/lZ3FTaHQ+tDkGPY=</ds:DigestValue>\n" +
            "\t\t</ds:Reference>\n" +
            "\t</ds:SignedInfo>\n" +
            "\t<ds:SignatureValue>\n" +
            "je7u6X9J06RDilNFfNrUqk3gqxf3NTwPj4mjCZYEqHXHiIZzbMauUks8PlKGkvj9\n" +
            "T7Gk3VAxwDXEufVXYKmC8rLvGxR+3sdWfmUqM7vZ747hwrFt+GcJGawuoCUbcDSm\n" +
            "TkXkbCvZHf3af2XBghwLWqBuWd3YFWR1SLgtFoejC4+2LF/PFZnvQHKP/3hUjT2l\n" +
            "CNh+xyczRtJvrQw6Q/UkPQ==\n" +
            "\t</ds:SignatureValue>\n" +
            "\t<ds:KeyInfo>\n" +
            "\t\t<ds:KeyValue>\n" +
            "\t\t\t<ds:RSAKeyValue>\n" +
            "\t\t\t\t<ds:Modulus>\n" +
            "x0ICU0P7PrRjVuBTm6c+komVb7m2egkk2rVkt0kH4Co1wxyiCy3CwVGnpBdWGW4g\n" +
            "NC5fB9V/K/Ufz+vF9gxzjOJcgiMGw00tvKioxWINwjzcaF8Vl/930kAUlxBTqVUf\n" +
            "d0cjmXYX/zt2GmPn288UhQ==\n" +
            "\t\t\t\t</ds:Modulus>\n" +
            "\t\t\t\t<ds:Exponent>AQAB</ds:Exponent>\n" +
            "\t\t\t</ds:RSAKeyValue>\n" +
            "\t\t</ds:KeyValue>\n" +
            "\t\t<ds:X509Data>\n" +
            "\t\t\t<ds:X509IssuerSerial>\n" +
            "\t\t\t\t<ds:X509IssuerName>2.5.4.97=#0C10564154504C2D35313730333539343538, CN=Certum QCA 2017, O=Asseco Data Systems S.A., C=PL</ds:X509IssuerName>\n" +
            "\t\t\t\t<ds:X509SerialNumber>141653250501173945242350161858492227064</ds:X509SerialNumber>\n" +
            "\t\t\t</ds:X509IssuerSerial>\n" +
            "\t\t\t<ds:X509SubjectName>C=PL, 2.5.4.5=#1311504E4F504C2D3737303131323133323234, 2.5.4.4=#0C0XF45677, 2.5.4.42=#0C0XXXXX, CN=Anonymous</ds:X509SubjectName>\n" +
            "\t\t\t<ds:X509Certificate>\n" +
            "MIIHcTCCBVmgAwIBAgIQapFsdq+IiduSmtJ2Q3nd+DANBgkqhkiG9w0BAQsFADBl\n" +
            "EDBw2v24ZxbL+C3wi56m83R+Ls8CacIkWyqBA77ogm0Alm2EngFQkpDR1Qq+nTwp\n" +
            "N5nNIsDAZSIXwud+BgtQ8f1nkm0SsRxPqXYkw3AE4W7PE0B3RiyBoVtz6UBZtRSC\n" +
            "60PCVoA0QNH/eGmS3qy7QFvp2fCaGl+W3yMu84IVm4Ic3wRlfQ==\n" +
            "\t\t\t</ds:X509Certificate>\n" +
            "\t\t</ds:X509Data>\n" +
            "\t</ds:KeyInfo>\n" +
            "\t<ds:Object>\n" +
            "\t\t<xades:QualifyingProperties xmlns:xades=\"http://uri.etsi.org/01903/v1.3.2#\" Target=\"#Signature-368352260\">\n" +
            "\t\t\t<xades:SignedProperties Id=\"SignedProperties-1862011922\">\n" +
            "\t\t\t\t<xades:SignedSignatureProperties>\n" +
            "\t\t\t\t\t<xades:SigningTime>2020-02-11T12:16:34.816Z</xades:SigningTime>\n" +
            "\t\t\t\t\t<xades:SigningCertificate>\n" +
            "\t\t\t\t\t\t<xades:Cert>\n" +
            "\t\t\t\t\t\t\t<xades:CertDigest>\n" +
            "\t\t\t\t\t\t\t\t<ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>\n" +
            "\t\t\t\t\t\t\t\t<ds:DigestValue>4ufAKF1pC/e3RF1S7+WBgWn05nYXtgkcjYT2EmEIkG0=</ds:DigestValue>\n" +
            "\t\t\t\t\t\t\t</xades:CertDigest>\n" +
            "\t\t\t\t\t\t\t<xades:IssuerSerial>\n" +
            "\t\t\t\t\t\t\t\t<ds:X509IssuerName>2.5.4.97=#0C10564154504C2D35313730333539343538, CN=Certum QCA 2017, O=Asseco Data Systems S.A., C=PL</ds:X509IssuerName>\n" +
            "\t\t\t\t\t\t\t\t<ds:X509SerialNumber>141653250501173945242350161858492227064</ds:X509SerialNumber>\n" +
            "\t\t\t\t\t\t\t</xades:IssuerSerial>\n" +
            "\t\t\t\t\t\t</xades:Cert>\n" +
            "\t\t\t\t\t</xades:SigningCertificate>\n" +
            "\t\t\t\t</xades:SignedSignatureProperties>\n" +
            "\t\t\t\t<xades:SignedDataObjectProperties>\n" +
            "\t\t\t\t\t<xades:DataObjectFormat ObjectReference=\"\">\n" +
            "\t\t\t\t\t\t<xades:Description>BINARY_FORMAT []</xades:Description>\n" +
            "\t\t\t\t\t\t<xades:MimeType>text/xml</xades:MimeType>\n" +
            "\t\t\t\t\t\t<xades:Encoding>http://www.w3.org/2000/09/xmldsig#base64</xades:Encoding>\n" +
            "\t\t\t\t\t</xades:DataObjectFormat>\n" +
            "\t\t\t\t</xades:SignedDataObjectProperties>\n" +
            "\t\t\t</xades:SignedProperties>\n" +
            "\t\t</xades:QualifyingProperties>\n" +
            "\t</ds:Object>\n" +
            "</ds:Signature>\n" +
            "</Deklaracja>";

    @Test
    public void getPersonFromInputStreamMethodTest() {
        try {
            PersonPIT2019Handler personPIT2019Handler = new PersonPIT2019Handler();
            InputStream inputStream = IOUtils.toInputStream(xml, StandardCharsets.UTF_8);
            PersonPIT personPIT = personPIT2019Handler.getPersonFromInputStream(inputStream);
            Assert.assertEquals("Persons name should return required value", "Aneta", personPIT.getName());
            Assert.assertEquals("Persons name should return required value", "Doe", personPIT.getSurName());
            Assert.assertEquals("Persons name should return required value", "19833.94", personPIT.getPersonData().get("P_31").getData().toString());
        } catch (ParserConfigurationException | IOException | SAXException e) {
            Assert.fail();
        }
    }


}
