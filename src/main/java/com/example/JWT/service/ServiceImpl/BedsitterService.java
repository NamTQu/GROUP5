package com.example.JWT.service.ServiceImpl;

import com.example.JWT.dto.Request.BedsitterRequest;
import com.example.JWT.dto.Response.BedsitterResponse;
import com.example.JWT.dto.Response.UserResponse;
import com.example.JWT.model.Entity.Bedsitter;
import com.example.JWT.model.Entity.User;
import com.example.JWT.repository.BedsitterRepository;
import com.example.JWT.service.Interface.IBedsitterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BedsitterService implements IBedsitterService {

    @Autowired
    private final BedsitterRepository bedsitterRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public List<BedsitterRequest> getHomePageProducts(int numberProducts){
        return bedsitterRepository.findTopProducts(numberProducts);
    }

    @Override
    public BedsitterResponse getBedDetails(Long id) {
        Bedsitter bedsitterDetails = bedsitterRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Bedsitter Not Found!"));
        return modelMapper.map(bedsitterDetails, BedsitterResponse.class);
    }

    @Override
    public Page<BedsitterResponse> getAllBedsitters(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Bedsitter> bedsitterPage = bedsitterRepository.findAll(pageable);

        return bedsitterPage.map(bedsitter -> modelMapper.map(bedsitter, BedsitterResponse.class));
    }

    @Override
    public Page<BedsitterResponse> getAllBedsitter(int page, int size, String keyWord) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Bedsitter> bedsitterPage;
        if(keyWord == null){
            bedsitterPage = bedsitterRepository.findAll(pageable);
        }else{
            bedsitterPage = bedsitterRepository.findByroomCodeContaining(keyWord, pageable);
        }
        return bedsitterPage.map(bedsitter ->modelMapper.map(bedsitter, BedsitterResponse.class));
    }


    public Bedsitter editBedsitter(Long id, Bedsitter updatedBedsitter) {
        Bedsitter existingBedsitter = bedsitterRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Bedsitter Not Found"));
        existingBedsitter.setSize(updatedBedsitter.getSize());
        existingBedsitter.setElectricityPrice(updatedBedsitter.getElectricityPrice());
        existingBedsitter.setWaterPrice(updatedBedsitter.getWaterPrice());
        existingBedsitter.setRoomPrice(updatedBedsitter.getRoomPrice());
        return bedsitterRepository.save(existingBedsitter);
    }

    public void deleteBedsitter(Long id) {
        Bedsitter existingBedsitter = bedsitterRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Bedsitter Not Found"));
        bedsitterRepository.delete(existingBedsitter);
    }
}
