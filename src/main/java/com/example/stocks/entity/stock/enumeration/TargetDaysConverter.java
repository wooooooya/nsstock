package com.example.stocks.entity.stock.enumeration;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TargetDaysConverter implements AttributeConverter<TargetDays, String> {

    @Override
    public String convertToDatabaseColumn(TargetDays attribute) {
        if (attribute == null) return null;
        return attribute.getValue();
    }

    @Override
    public TargetDays convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (TargetDays type : TargetDays.values()) {
            if (type.getValue().equals(dbData)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + dbData);
    }
}