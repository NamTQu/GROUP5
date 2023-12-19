package com.example.JWT.service.ServiceImpl;

import com.example.JWT.dto.Request.ChangePasswordRequest;
import com.example.JWT.dto.Request.ResetPasswordRequest;
import com.example.JWT.dto.Response.BedsitterResponse;
import com.example.JWT.dto.Response.UserResponse;
import com.example.JWT.model.Entity.Bedsitter;
import com.example.JWT.model.Entity.User;
import com.example.JWT.repository.userRepository;
import com.example.JWT.service.Interface.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final userRepository repository;


    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var User = (com.example.JWT.model.Entity.User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), User.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }

        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }
        User.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(User);
    }

    @Override
    public Object resetPassword(ResetPasswordRequest request) {

        return null;
    }


    public User editProfile (Integer userId, User updatedUser){
        User existingUser = repository.findById(userId).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setUsername(updatedUser.getUsername());
            return repository.save(existingUser);
        }
        return null;
    }

    public Page<UserResponse> getAllUsers(int page, int size, String keyWord) {
        Pageable pageable = PageRequest.of(page,size);
        Page<User> userPage;
        if(keyWord == null){
            userPage = repository.findAll(pageable);
        }else{
            userPage = repository.findByfullnameContaining(keyWord, pageable);
        }
        return userPage.map(user ->modelMapper.map(user,UserResponse.class));
    }

    public UserResponse getUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            return null;
        }
        Integer userId = ((User) authentication.getPrincipal()).getId();
        User user = repository.findById(userId).orElseThrow(()->new EntityNotFoundException("User not found"));
        return modelMapper.map(user, UserResponse.class);
    }


}
