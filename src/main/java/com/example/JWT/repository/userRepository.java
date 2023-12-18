package com.example.JWT.repository;

import com.example.JWT.dto.Response.UserResponse;
import com.example.JWT.model.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface userRepository extends JpaRepository<User,Integer> {

     Optional<User> findById(Long userId);

    Optional<User> findByUsername(String username);

    @Query(value = """
            select * from Bedsitter_Management_System.Account where id = :user_id
            """, nativeQuery = true)
    UserResponse viewProfile(@Param("user_id") int user_id);


    Page<User> findBynameContaining(String keyWord, Pageable pageable);
}
