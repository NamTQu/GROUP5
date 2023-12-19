package com.example.JWT.repository;

import com.example.JWT.dto.Request.BedsitterRequest;
import com.example.JWT.dto.Response.BedsitterResponse;
import com.example.JWT.model.Entity.Bedsitter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BedsitterRepository extends JpaRepository<Bedsitter, Long> {

    @Query(value = """
            select * from Bedsitter_Management_System.Bedsitter LIMIT :numberProducts
            """,nativeQuery = true)
    public List<BedsitterRequest> findTopProducts(@Param("numberProducts") int numberProducts);

    public Page<Bedsitter> findByroomCodeContaining(String keyWord, Pageable pageable);

    public Optional<Bedsitter> findById(Long id);

}
