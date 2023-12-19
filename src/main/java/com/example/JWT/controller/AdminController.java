package com.example.JWT.controller;


import com.example.JWT.dto.Response.BedsitterResponse;
import com.example.JWT.dto.Response.UserResponse;
import com.example.JWT.model.Entity.Bedsitter;
import com.example.JWT.repository.userRepository;
import com.example.JWT.service.ServiceImpl.BedsitterService;
import com.example.JWT.service.ServiceImpl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/landlord")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private final userRepository repository;

    @Autowired
    private final UserService userService;

    @Autowired
    private final BedsitterService bedsitterService;

    @GetMapping("/userlist")
    public ResponseEntity<Page<UserResponse>> getAllUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyWord
    ){
          return ResponseEntity.ok(userService.getAllUsers(page, size, keyWord));
    }

    @GetMapping("/roomlist")
    public ResponseEntity<Page<BedsitterResponse>> getAllBedsitter(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyWord
    ){
        return ResponseEntity.ok(bedsitterService.getAllBedsitter(page, size, keyWord));
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Bedsitter> editBedsitter(
            @PathVariable Long id,
            @RequestBody Bedsitter updatedBedsitter
    ){
        Bedsitter editedBedsitter = bedsitterService.editBedsitter(id, updatedBedsitter);
        return ResponseEntity.ok(editedBedsitter);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBedsitter(
            @PathVariable Long id
    ){
        bedsitterService.deleteBedsitter(id);
        return ResponseEntity.ok("Bedsitter deleted successfully");
    }




}
