package com.example.JWT.model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "bedsitter_id", referencedColumnName = "bedsitter_id")
    private Bedsitter bedsitter;

    @ManyToOne
    @JoinColumn(name = "announcement_id", referencedColumnName = "announcement_id")
    private Announcement announcement;

    @Column(name = "image", nullable = false, columnDefinition = "LONGTEXT")
    private String image;


}
