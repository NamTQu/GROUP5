package com.example.JWT.controller;

import com.example.JWT.dto.Response.BedsitterResponse;
import com.example.JWT.service.ServiceImpl.BedsitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bedsitter")
@RequiredArgsConstructor
public class BedsitterController {
    private final BedsitterService bedsitterService;

    @GetMapping("/details")
    public ResponseEntity<BedsitterResponse> getBedsitterDetails(@RequestParam int id){
        return new ResponseEntity<>(bedsitterService.getBedDetails(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<BedsitterResponse>> getAllBedsitter(@RequestParam(defaultValue = "0") int page ,
                                                                   @RequestParam(defaultValue = "5") int size
    ){
        return new ResponseEntity<>(bedsitterService.getAllBedsitters(page, size), HttpStatus.OK) ;
    }

}
