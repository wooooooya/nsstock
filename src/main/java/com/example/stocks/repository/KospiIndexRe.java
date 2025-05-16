package com.example.stocks.repository;

import com.example.stocks.entity.KospiIndexEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KospiIndexRe extends JpaRepository<KospiIndexEn, Long> {
}