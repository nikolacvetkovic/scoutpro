package xyz.riocode.scoutpro.jpa.converter;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SetStringStringConverter implements AttributeConverter<Set<String>, String> {
    @Override
    public String convertToDatabaseColumn(Set<String> strings) {
        if(strings.size() == 0) return null;
        return String.join(",", strings);
    }

    @Override
    public Set<String> convertToEntityAttribute(String s) {
        if (s == null) return Collections.EMPTY_SET;
        return new HashSet<>(Arrays.asList(s.split(",")));
    }
}
