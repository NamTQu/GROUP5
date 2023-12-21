package com.example.JWT.model.Entity;

import com.example.JWT.model.Enum.TokenType;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "Token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType token_type;

    private boolean expired;

    private boolean revoked;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;
}
