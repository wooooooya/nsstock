package com.example.stocks.entity.stock;

import com.example.stocks.entity.stock.enumeration.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name="stock_info")
public class StockInfoEn {

    @Id
    @Column(name = "short_code", length = 9)
    private String shortCode;

    @Column(name = "standard_code", length = 12)
    private String standardCode;

    @Column(name = "kor_stock_name", length = 240)
    private String korStockName;

    @Column(name = "kor_short_stock_name", length = 20)
    private String korShortStockName;

    @Column(name = "eng_stock_name", length = 100)
    private String engStockName;

    @Column(name = "listing_date")
    private LocalDate listingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "market_type", nullable = false)
    private MarketType marketType;

    @Column(name = "certificate_type")
    private CertificateType certificateType;

    @Column(name = "department", length = 100)
    private String department;


    @Column(name = "stock_type")
    private StockType stockType;

    @Column(name = "face_value")
    private Integer faceValue;

    @Column(name = "listed_stock_num")
    private Long listedStockNum;

    @Column(name = "corporate_name", length = 240)
    private String corporateName;

    @Column(name = "corporate_num", length = 20)
    private String corporateNum;

    @OneToMany(mappedBy = "stockInfo", fetch = FetchType.LAZY)
    private List<StockPriceEn> stockPrices;
}