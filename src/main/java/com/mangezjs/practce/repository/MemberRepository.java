package com.mangezjs.practce.repository;

import com.mangezjs.practce.model.entity.MemberEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    MemberEntity findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE MemberEntity m SET m.password = :password WHERE m.id = :id")
    void updatePw(@Param("password") String password, @Param("id") Long id);

}
