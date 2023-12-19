package com.example.JWT.service.ServiceImpl;

import com.example.JWT.model.Entity.Image;
import com.example.JWT.repository.ImageRepository;
import com.example.JWT.service.Interface.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    @Autowired
    private final ImageRepository imageRepository;

    @Override
    public List<Image> getImagesByBedsitterId(Long bedsitterId) {
        return imageRepository.findByBedsitter_BedsitterId(bedsitterId);
    }

    @Override
    public String getImageUrlFromDatabase(Image image) {
        return image.getImage();
    }
}
