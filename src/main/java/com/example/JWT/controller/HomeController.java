package com.example.JWT.controller;

import com.example.JWT.dto.Request.BedsitterRequest;
import com.example.JWT.service.ServiceImpl.BedsitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomeController {

    private final BedsitterService bedsitterService;
    @GetMapping("/")
    public ResponseEntity<List<BedsitterRequest>> homePage(){
        return  new ResponseEntity<>(bedsitterService.getHomePageProducts(5), HttpStatus.OK);
    }

}
