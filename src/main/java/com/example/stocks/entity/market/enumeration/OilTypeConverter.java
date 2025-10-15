package com.example.stocks.entity.market.enumeration;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OilTypeConverter implements AttributeConverter<OilType, String> {

    @Override
    public String convertToDatabaseColumn(OilType oilType) {
        if (oilType == null) {
            return null;
        }
        return oilType.getLabel();
    }

    @Override
    public OilType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (OilType type : OilType.values()) {
            if (type.getLabel().equals(dbData)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown label: " + dbData);
    }
}