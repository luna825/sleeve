package com.moonyue.sleeve.repository;

import com.moonyue.sleeve.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOpenid(String openId);

    Optional<User> findFirstByEmail(String email);

    Optional<User> findFirstByNickname(String nickname);
}
