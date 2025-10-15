package com.example.stocks.entity.stock.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CertificateType {
    INVESTMENT_COMPANY("투자회사"),
    DEPOSITORY_RECEIPT("주식예탁증권"),
    STOCK_CERTIFICATE("주권"),
    REAL_ESTATE_INVESTMENT_COMPANY("부동산투자회사"),
    INFRASTRUCTURE_FUND("사회간접자본투융자회사"), // Social Overhead Capital Investment Company
    FOREIGN_STOCK_CERTIFICATE("외국주권");

    private final String label;
}