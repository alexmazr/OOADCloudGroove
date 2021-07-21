package com.cloudgroove.userservice.repository;


import com.cloudgroove.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Abstracted DB queries using JPA
@Repository
public interface UserRepository extends JpaRepository<User, String>
{
    User findUserByEmail (String email);
}
