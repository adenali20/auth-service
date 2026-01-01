package com.adenali.fms.repository;

import com.adenali.fms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User save(User entity);

    User findUserByEmail(String email);
}
