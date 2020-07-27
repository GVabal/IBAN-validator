import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class IBANNumberValidator {

    private final Map<String, IBANCountry> IBANCountries = new HashMap<>();

    public IBANNumberValidator() {
        List<String> lines = new ArrayList<>();

        lines.add("Andorra,AD,24,\\d{4}\\d{4}\\w{12},AD1200012030200359100100,no");
        lines.add("United Arab Emirates,AE,23,\\d{3}\\d{16},AE070331234567890123456,no");
        lines.add("Albania,AL,28,\\d{8}\\d{16},AL47212110090000000235698741,no");
        lines.add("Austria,AT,20,\\d{5}\\d{11},AT611904300234573201,yes");
        lines.add("Azerbaijan,AZ,28,[A-z]{4}\\w{20},AZ21NABZ00000000137010001944,no");
        lines.add("Bosnia and Herzegovina,BA,20,\\d{3}\\d{3}\\d{8}\\d{2},BA391290079401028494,no");
        lines.add("Belgium,BE,16,\\d{3}\\d{7}\\d{2},BE68539007547034,yes");
        lines.add("Bulgaria,BG,22,[A-z]{4}\\d{4}\\d{2}\\w{8},BG80BNBG96611020345678,yes");
        lines.add("Bahrain,BH,22,[A-z]{4}\\w{14},BH67BMAG00001299123456,no");
        lines.add("Brazil,BR,29,\\d{8}\\d{5}\\d{10}[A-z]{1}\\w{1},BR9700360305000010009795493P1,no");
        lines.add("Belarus,BY,28,[A-z]{4}\\d{4}\\w{16},BY13NBRB3600900000002Z00AB00,no");
        lines.add("Switzerland,CH,21,\\d{5}\\w{12},CH9300762011623852957,yes");
        lines.add("Costa Rica,CR,22,\\d{4}\\d{14},CR05015202001026284066,no");
        lines.add("Cyprus,CY,28,\\d{3}\\d{5}\\w{16},CY17002001280000001200527600,yes");
        lines.add("Czechia,CZ,24,\\d{4}\\d{6}\\d{10},CZ6508000000192000145399,yes");
        lines.add("Germany,DE,22,\\d{8}\\d{10},DE89370400440532013000,yes");
        lines.add("Denmark,DK,18,\\d{4}\\d{9}\\d{1},DK5000400440116243,yes");
        lines.add("Dominican Republic,DO,28,[A-z]{4}\\d{20},DO28BAGR00000001212453611324,no");
        lines.add("Estonia,EE,20,\\d{2}\\d{2}\\d{11}\\d{1},EE382200221020145685,yes");
        lines.add("Spain,ES,24,\\d{4}\\d{4}\\d{1}\\d{1}\\d{}10,ES9121000418450200051332,yes");
        lines.add("Finland,FI,18,\\d{6}\\d{7}\\d{1},FI2112345600000785,yes");
        lines.add("Faroe Islands,FO,18,\\d{4}\\d{9}\\d{1},FO6264600001631634,no");
        lines.add("France,FR,27,\\d{5}\\d{5}\\w{11}\\d{2},FR1420041010050500013M02606,yes");
        lines.add("United Kingdom,GB,22,[A-z]{4}\\d{6}\\d{8},GB29NWBK60161331926819,yes");
        lines.add("Georgia,GE,22,[A-z]{2}\\d{16},GE29NB0000000101904917,no");
        lines.add("Gibraltar,GI,23,[A-z]{4}\\w{15},GI75NWBK000000007099453,yes");
        lines.add("Greenland,GL,18,\\d{4}\\d{9}\\d{1},GL8964710001000206,no");
        lines.add("Greece,GR,27,\\d{3}\\d{4}\\d{16},GR1601101250000000012300695,yes");
        lines.add("Guatemala,GT,28,\\w{4}\\w{20},GT82TRAJ01020000001210029690,no");
        lines.add("Croatia,HR,21,\\d{7}\\d{10},HR1210010051863000160,yes");
        lines.add("Hungary,HU,28,\\d{3}\\d{4}\\d{1}\\d{15}\\d{1},HU42117730161111101800000000,yes");
        lines.add("Ireland,IE,22,[A-z]{4}\\d{6}\\d{8},IE29AIBK93115212345678,yes");
        lines.add("Israel,IL,23,\\d{3}\\d{3}\\d{13},IL620108000000099999999,no");
        lines.add("Iceland,IS,26,\\d{4}\\d{2}\\d{6}\\d{10},IS140159260076545510730339,yes");
        lines.add("Iraq,IQ,23,\\w{4}\\d{3}\\d{12},IQ98NBIQ850123456789012,no");
        lines.add("Italy,IT,27,[A-z]{1}\\d{5}\\d{5}\\w{12},IT60X0542811101000000123456,yes");
        lines.add("Jordan,JO,30,[A-z]{4}\\d{4}\\w{18},JO94CBJO0010000000000131000302,no");
        lines.add("Kuwait,KW,30,[A-z]{4}\\w{22},KW81CBKU0000000000001234560101,no");
        lines.add("Kazakhstan,KZ,20,F03A13\\d{3}\\w{13},KZ86125KZT5004100100,no");
        lines.add("Lebanon,LB,28,\\d{4}\\w{20},LB62099900000001001901229114,no");
        lines.add("Saint Lucia,LC,32,[A-z]{4}\\w{24},LC55HEMM000100010012001200023015,no");
        lines.add("Liechtenstein,LI,21,\\d{5}\\w{12},LI21088100002324013AA,yes");
        lines.add("Lithuania,LT,20,\\d{5}\\d{11},LT121000011101001000,yes");
        lines.add("Luxembourg,LU,20,\\d{3}\\w{13},LU280019400644750000,yes");
        lines.add("Latvia,LV,21,[A-z]{4}\\w{13},LV80BANK0000435195001,yes");
        lines.add("Monaco,MC,27,\\d{5}\\d{5}\\w{11}\\d{2},MC5811222000010123456789030,yes");
        lines.add("Moldova,MD,24,\\w{2}\\w{18},MD24AG000225100013104168,no");
        lines.add("Montenegro,ME,22,\\d{3}\\d{13}\\d{2},ME25505000012345678951,no");
        lines.add("Macedonia,MK,19,\\d{3}\\w{10}\\d{2},MK07250120000058984,no");
        lines.add("Mauritania,MR,27,\\d{5}\\d{5}\\d{11}\\d{2},MR1300020001010000123456753,no");
        lines.add("Malta,MT,31,[A-z]{4}\\d{5}\\w{18},MT84MALT011000012345MTLCAST001S,yes");
        lines.add("Mauritius,MU,30,[A-z]{4}\\d{2}\\d{2}\\d{12}\\d{3}[A-z]{3},MU17BOMM0101101030300200000MUR,no");
        lines.add("Netherlands,NL,18,[A-z]{4}\\d{10},NL91ABNA0417164300,yes");
        lines.add("Norway,NO,15,\\d{4}\\d{6}\\d{1},NO9386011117947,yes");
        lines.add("Pakistan,PK,24,[A-z]{4}\\w{16},PK36SCBL0000001123456702,no");
        lines.add("Poland,PL,28,\\d{8}\\d{16},PL61109010140000071219812874,yes");
        lines.add("Palestine,PS,29,U04A21[A-z]{4}\\w{21},PS92PALS000000000400123456702,no");
        lines.add("Portugal,PT,25,\\d{4}\\d{4}\\d{11}\\d{2},PT50000201231234567890154,yes");
        lines.add("Qatar,QA,29,[A-z]{4}\\w{21},QA58DOHB00001234567890ABCDEFG,no");
        lines.add("Romania,RO,24,[A-z]{4}\\w{16},RO49AAAA1B31007593840000,yes");
        lines.add("Serbia,RS,22,\\d{3}\\d{13}\\d{2},RS35260005601001611379,no");
        lines.add("Saudi Arabia,SA,24,\\d{2}\\w{18},SA0380000000608010167519,no");
        lines.add("Seychelles,SC,31,[A-z]{4}\\d{2}\\d{2}\\d{16}[A-z]{3},SC18SSCB11010000000000001497USD,no");
        lines.add("Sweden,SE,24,\\d{3}\\d{16}\\d{1},SE4550000000058398257466,yes");
        lines.add("Slovenia,SI,19,\\d{5}\\d{8}\\d{2},SI56263300012039086,yes");
        lines.add("Slovakia,SK,24,\\d{4}\\d{6}\\d{10},SK3112000000198742637541,yes");
        lines.add("San Marino,SM,27,[A-z]{1}\\d{5}\\d{5}\\w{12},SM86U0322509800000000270100,yes");
        lines.add("Sao Tome and Principe,ST,25,\\d{8}\\d{11}\\d{2},ST68000100010051845310112,no");
        lines.add("El Salvador,SV,28,\\w{4}\\d{20},SV62CENR00000000000000700025,no");
        lines.add("Timor-Leste,TL,23,\\d{3}\\d{14}\\d{2},TL380080012345678910157,no");
        lines.add("Tunisia,TN,24,\\d{2}\\d{3}\\d{13}\\d{2},TN5910006035183598478831,no");
        lines.add("Turkey,TR,26,\\d{5}\\w{1}\\w{16},TR330006100519786457841326,no");
        lines.add("Ukraine,UA,29,\\d{6}\\w{19},UA213996220000026007233566001,no");
        lines.add("Vatican City State,VA,22,\\d{3}\\d{15},VA59001123000012345678,no");
        lines.add("Virgin Islands,VG,24,[A-z]{4}\\d{16},VG96VPVG0000012345678901,no");
        lines.add("Kosovo,XK,20,\\d{4}\\d{10}\\d{2},XK051212012345678906,no");

        for (String line : lines) {
            String[] e = line.split(",");
            try {
                IBANCountries.put(e[1].toUpperCase(), new IBANCountry(e[0], e[1], e[2], e[3], e[4], e[5]));
            } catch (InputMismatchException ex) {
                System.err.print(ex.getMessage() + " ");
                System.err.println(line + " will not be added to IBAN countries list!");
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IBANCountryValidator{");
        sb.append("IBANCountries=").append(IBANCountries);
        sb.append('}');
        return sb.toString();
    }

    public boolean validateFormat(String number) {
        return Pattern.matches("[A-z]{2}\\d{2}.+", number);
    }

    public boolean validateCountry(String number) {
        String symbol = number.substring(0,2);
        return IBANCountries.containsKey(symbol);
    }

    public boolean validateLength(String number) {
        return number.length() == IBANCountries.get(number.substring(0,2)).getLength();
    }

    public boolean validateStructure(String number) {
        return Pattern.matches(IBANCountries.get(number.substring(0,2)).getStructure(), number.substring(4));
    }

    public boolean validateMod97(String number) {
        BigInteger bigInteger = new BigInteger(convertIBANToNumber(number));
        return bigInteger.mod(new BigInteger("97")).equals(BigInteger.ONE);
    }

    public boolean validateIBAN(String number) {
        if (!validateFormat(number))
            throw new RuntimeException("Format is invalid!");
        if (!validateCountry(number))
            throw new RuntimeException("Country is not in IBAN list!");
        if (!validateLength(number))
            throw new RuntimeException("Length is incorrect!");
        if (!validateStructure(number))
            throw new RuntimeException("Structure is invalid!");
        if (!validateMod97(number))
            throw new RuntimeException("Number is invalid!");
        return true;
    }

    public String convertIBANToNumber(String number) {
        number = number.substring(4) + number.substring(0,4);
        StringBuilder result = new StringBuilder();
        for (Character c : number.toUpperCase().toCharArray()) {
            switch (c) {
                case '0':
                    result.append(0);
                    break;
                case '1':
                    result.append(1);
                    break;
                case '2':
                    result.append(2);
                    break;
                case '3':
                    result.append(3);
                    break;
                case '4':
                    result.append(4);
                    break;
                case '5':
                    result.append(5);
                    break;
                case '6':
                    result.append(6);
                    break;
                case '7':
                    result.append(7);
                    break;
                case '8':
                    result.append(8);
                    break;
                case '9':
                    result.append(9);
                    break;
                case 'A':
                    result.append(10);
                    break;
                case 'B':
                    result.append(11);
                    break;
                case 'C':
                    result.append(12);
                    break;
                case 'D':
                    result.append(13);
                    break;
                case 'E':
                    result.append(14);
                    break;
                case 'F':
                    result.append(15);
                    break;
                case 'G':
                    result.append(16);
                    break;
                case 'H':
                    result.append(17);
                    break;
                case 'I':
                    result.append(18);
                    break;
                case 'J':
                    result.append(19);
                    break;
                case 'K':
                    result.append(20);
                    break;
                case 'L':
                    result.append(21);
                    break;
                case 'M':
                    result.append(22);
                    break;
                case 'N':
                    result.append(23);
                    break;
                case 'O':
                    result.append(24);
                    break;
                case 'P':
                    result.append(25);
                    break;
                case 'Q':
                    result.append(26);
                    break;
                case 'R':
                    result.append(27);
                    break;
                case 'S':
                    result.append(28);
                    break;
                case 'T':
                    result.append(29);
                    break;
                case 'U':
                    result.append(30);
                    break;
                case 'V':
                    result.append(31);
                    break;
                case 'W':
                    result.append(32);
                    break;
                case 'X':
                    result.append(33);
                    break;
                case 'Y':
                    result.append(34);
                    break;
                case 'Z':
                    result.append(35);
                    break;
            }
        }
        return result.toString();
    }
}
