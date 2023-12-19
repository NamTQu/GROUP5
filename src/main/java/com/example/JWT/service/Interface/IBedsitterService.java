package com.example.JWT.service.Interface;

import com.example.JWT.dto.Request.BedsitterRequest;
import com.example.JWT.dto.Response.BedsitterResponse;
import com.example.JWT.model.Entity.Bedsitter;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBedsitterService {
    public List<BedsitterRequest> getHomePageProducts(int numberProducts);
    public BedsitterResponse getBedDetails(Long id);
    public Page<Bedsitter> getAllBedsitters(int page, int size);
    public Page<BedsitterResponse> getAllBedsitter(int page, int size, String keyWord);
    public Bedsitter editBedsitter(Long id, Bedsitter updatedBedsitter);
    public void deleteBedsitter(Long id);
}
