package com.example.JWT.repository;

import com.example.JWT.model.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByBedsitter_BedsitterId(Long bedsitterId);
}
