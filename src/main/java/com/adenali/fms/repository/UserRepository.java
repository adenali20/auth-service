package com.adenali.fms.repository;

import com.adenali.fms.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<AppUser,Long> {
    AppUser save(AppUser entity);

    List<AppUser> findAll();

    AppUser findUserByEmail(String email);
}
