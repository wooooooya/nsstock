package com.example.stocks.repository;

import com.example.stocks.entity.KospiIndexEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface KospiIndexRe extends JpaRepository<KospiIndexEn, LocalDate> {
    @Query(value = "SELECT * FROM kospi_index WHERE date = :date", nativeQuery = true)
    Optional<KospiIndexEn> findByDate(@Param("date") LocalDate date);
}