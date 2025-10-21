package com.example.stocks.entity.stock.enumeration;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CertificateTypeConverter implements AttributeConverter<CertificateType, String> {
    @Override
    public String convertToDatabaseColumn(CertificateType certificateType) {
        if (certificateType == null) {
            return null;
        }
        return certificateType.getLabel();
    }

    @Override
    public CertificateType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (CertificateType type : CertificateType.values()) {
            if (type.getLabel().equals(dbData)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown label: " + dbData);
    }
}