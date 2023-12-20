package com.example.JWT.controller;

import com.example.JWT.dto.Response.BedsitterResponse;
import com.example.JWT.dto.Response.ImageResponse;
import com.example.JWT.model.Entity.Bedsitter;
import com.example.JWT.model.Entity.Image;
import com.example.JWT.service.ServiceImpl.BedsitterService;
import com.example.JWT.service.ServiceImpl.ImageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/bedsitter")
@RequiredArgsConstructor
public class BedsitterController {

    @Autowired
    private final BedsitterService bedsitterService;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final ImageService imageService;

    @GetMapping("/details")
    public ResponseEntity<BedsitterResponse> getBedsitterDetails(@RequestParam Long id){
        return new ResponseEntity<>(bedsitterService.getBedDetails(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<BedsitterResponse>> getAllBedsitter(@RequestParam(defaultValue = "0") int page ,
                                                                   @RequestParam(defaultValue = "5") int size
    ){

        Page<Bedsitter> bedsitterPage = bedsitterService.getAllBedsitters(page, size);
        Page<BedsitterResponse> bedsitterResponses = bedsitterPage.map(bedsitter ->
        {
            BedsitterResponse response = modelMapper.map(bedsitter, BedsitterResponse.class);
            List<Image> images = imageService.getImagesByBedsitterId(bedsitter.getBedsitterId());
            List<ImageResponse> imageResponses = images.stream()
                    .map(image -> {
                        // Đảm bảo đường dẫn ảnh truyền vào đúng
                        String imageUrl = imageService.getImageUrlFromDatabase(image); // Hàm này để lấy đường dẫn ảnh từ đối tượng Image
                        image.setImage(imageUrl);
                        return modelMapper.map(image, ImageResponse.class);
                    })
                    .collect(Collectors.toList());
            response.setImages(imageResponses);
            return response;
        });
        return ResponseEntity.ok(bedsitterResponses);

    }

}
