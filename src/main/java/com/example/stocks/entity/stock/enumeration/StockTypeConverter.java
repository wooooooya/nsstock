package com.example.stocks.entity.stock.enumeration;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StockTypeConverter implements AttributeConverter<StockType, String> {
    @Override
    public String convertToDatabaseColumn(StockType stockType) {
        if (stockType == null) {
            return null;
        }
        return stockType.getLabel();
    }

    @Override
    public StockType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (StockType type : StockType.values()) {
            if (type.getLabel().equals(dbData)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown label: " + dbData);
    }
}