package xyz.riocode.scoutpro.jpa.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SetStringStringConverterTest {

    SetStringStringConverter converter;

    Set<String> objectStrings;
    String columnString;

    @BeforeEach
    void setUp(){
        converter = new SetStringStringConverter();
        objectStrings = new HashSet<>(Arrays.asList("Heading", "Weighted Pass", "Man Marking"));
        columnString = "Heading,Weighted Pass,Man Marking";
    }

    @Test
    void testConvertToDatabaseColumnOk(){

        String result = converter.convertToDatabaseColumn(objectStrings);

        assertNotNull(result);
        assertThat(result).contains("Heading");
        assertEquals(columnString, result);
    }

    @Test
    void testConvertToDatabaseColumnEmptySet(){

        String result = converter.convertToDatabaseColumn(Collections.emptySet());

        assertNull(result);
    }

    @Test
    void testConvertToEntityAttributeOk(){

        Set<String> result = converter.convertToEntityAttribute(columnString);

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void testConvertToEntityAttributeNullColumnValue(){

        Set<String> result = converter.convertToEntityAttribute(null);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

}