package org.example.sweater.repository;

import org.example.sweater.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository <User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}
