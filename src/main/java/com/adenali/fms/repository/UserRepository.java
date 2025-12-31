package com.adenali.fms.repository;

import com.adenali.fms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User save(User entity);

    List<User> findAll();

    User findUserByEmail(String email);
}
