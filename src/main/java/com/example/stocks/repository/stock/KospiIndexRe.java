package com.example.stocks.repository.stock;

import com.example.stocks.entity.market.KospiIndexEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface KospiIndexRe extends JpaRepository<KospiIndexEn, LocalDate> {

    Optional<KospiIndexEn> findFirstByOrderByDateDesc();
    List<KospiIndexEn> findByDateGreaterThanEqualOrderByDateAsc(LocalDate startDate);
}