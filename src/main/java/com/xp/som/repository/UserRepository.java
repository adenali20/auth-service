package com.xp.som.repository;

import com.xp.som.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User save(User entity);

    List<User> findAll();

    User findByUsername(String username);
}
