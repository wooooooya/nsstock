package com.example.stocks.repository.user;

import com.example.stocks.entity.user.UserFavoriteEn;
import com.example.stocks.entity.user.UserFavoriteId;
import com.example.stocks.entity.user.UserInfoEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFavoriteRe extends JpaRepository<UserFavoriteEn, UserFavoriteId> { // <엔티티, 복합 키 타입>

    List<UserFavoriteEn> findAllByUserInfo(UserInfoEn userInfo);
    List<UserFavoriteEn> findAllByUserInfo_Uuid(String uuid);
}
