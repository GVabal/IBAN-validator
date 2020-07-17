import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IBANNumberValidatorTest {

    static IBANNumberValidator validator;

    @BeforeAll
    static void beforeAll() {
        validator = new IBANNumberValidator();
    }

    @Test
    void testValidateFormat() {
        assertTrue(validator.validateFormat("MD24AG000225100013104168"));
        assertTrue(validator.validateFormat("ST68000100010051840112"));
        assertTrue(validator.validateFormat("FR142"));
        assertFalse(validator.validateFormat("Not an IBAN number"));
        assertFalse(validator.validateFormat("5000400440116243"));
        assertFalse(validator.validateFormat("DKXX00400440116243"));
        assertFalse(validator.validateFormat("AAAA"));
        assertFalse(validator.validateFormat("AA01"));
    }

    @Test
    void testValidateCountry() {
        assertTrue(validator.validateCountry("LT0000000"));
        assertTrue(validator.validateCountry("SE0000000"));
        assertFalse(validator.validateCountry("00SE0000000"));
        assertFalse(validator.validateCountry("ZZ0000000"));
    }

    @Test
    void testValidateLength() {
        assertTrue(validator.validateLength("IE29AIBK93115212345678"));
        assertTrue(validator.validateLength("KW81CBKU0000000000001234560101"));
        assertFalse(validator.validateLength("IE29AIBK93112345678"));
        assertFalse(validator.validateLength("IE29AIBK931152123654845678"));
    }

    @Test
    void testValidateStructure() {
        assertTrue(validator.validateStructure("BH67BMAG00001299123456"));
        assertTrue(validator.validateStructure("TR330006100519786457841326"));
        assertFalse(validator.validateStructure("XK0512D20123A5678906"));
        assertFalse(validator.validateStructure("BY13N11B3600900000002Z00AB00"));
    }

    @Test
    void testValidateMod97() {
        assertTrue(validator.validateMod97("BE68539007547034"));
        assertTrue(validator.validateMod97("IT60X0542811101000000123456"));
        assertFalse(validator.validateMod97("BE68539607547032"));
        assertFalse(validator.validateMod97("IT60X0542811501000700123456"));
    }

    @Test
    void testValidateIBAN() {
        assertTrue(validator.validateIBAN("MK07250120000058984"));

        Throwable e1 = assertThrows(
                RuntimeException.class, () -> validator.validateIBAN("4545ABC4545")
        );
        assertEquals("Format is invalid!", e1.getMessage());

        Throwable e2 = assertThrows(
                RuntimeException.class, () -> validator.validateIBAN("BB004545454545")
        );
        assertEquals("Country is not in IBAN list!", e2.getMessage());

        Throwable e3 = assertThrows(
                RuntimeException.class, () -> validator.validateIBAN("IE29AIBK93112345678")
        );
        assertEquals("Length is incorrect!", e3.getMessage());

        Throwable e4 = assertThrows(
                RuntimeException.class, () -> validator.validateIBAN("BY13N11B3600900000002Z00AB00")
        );
        assertEquals("Structure is invalid!", e4.getMessage());

        Throwable e5 = assertThrows(
                RuntimeException.class, () -> validator.validateIBAN("BE68539607547032")
        );
        assertEquals("Number is invalid!", e5.getMessage());
    }

    @Test
    void testConvertIBANToNumber() {
        assertEquals("10111213", validator.convertIBANToNumber("ABCD"));
        assertEquals("1011121356781234", validator.convertIBANToNumber("1234ABCD5678"));
    }
}
