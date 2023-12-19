package com.example.JWT.service.Interface;

import com.example.JWT.model.Entity.Image;

import java.util.List;

public interface IImageService {
    public List<Image> getImagesByBedsitterId(Long bedsitterId);
    public String getImageUrlFromDatabase(Image image);
}
