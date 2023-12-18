package com.example.JWT.service.Interface;

import com.example.JWT.dto.Request.BedsitterRequest;
import com.example.JWT.dto.Response.BedsitterResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBedsitterService {
    public List<BedsitterRequest> getHomePageProducts(int numberProducts);
    public BedsitterResponse getBedDetails(int id);

    public Page<BedsitterResponse> getAllBedsitters(int page, int size);

    public Page<BedsitterResponse> getAllBedsitter(int page, int size, String keyWord);
}
