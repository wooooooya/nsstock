package com.example.stocks.repository.user;

import com.example.stocks.entity.user.UserFavoriteEn;
import com.example.stocks.entity.user.UserInfoEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRe extends JpaRepository<UserInfoEn, String> {

    Optional<UserInfoEn> findByLoginId(String loginId);
    Optional<UserInfoEn> findByEmail(String email);
}