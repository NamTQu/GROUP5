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

public interface BedsitterRepository extends JpaRepository<Bedsitter, Long> {

    @Query(value = """
            select * from Bedsitter_Management_System.Bedsitter LIMIT :numberProducts
            """,nativeQuery = true)
    List<BedsitterRequest> findTopProducts(@Param("numberProducts") int numberProducts);

    @Query(value = """
            select b from Bedsitter_Management_System.Bedsitter b WHERE bedsitter_id = :bed_id
            """, nativeQuery = true)
    BedsitterResponse findById(@Param("bed_id") int bed_id);

    Page<Bedsitter> findByroomCodeContaining(String keyWord, Pageable pageable);
}
