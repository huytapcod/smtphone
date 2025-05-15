package com.hqshop.ecommerce.multivendor1.service;

import com.hqshop.ecommerce.multivendor1.dto.request.UserCreationRequest;
import com.hqshop.ecommerce.multivendor1.dto.request.UserUpdateRequest;
import com.hqshop.ecommerce.multivendor1.dto.response.UserResponse;
import com.hqshop.ecommerce.multivendor1.entity.User;
import com.hqshop.ecommerce.multivendor1.enums.Roles;
import com.hqshop.ecommerce.multivendor1.exception.AppException;
import com.hqshop.ecommerce.multivendor1.exception.Errorcode;
import com.hqshop.ecommerce.multivendor1.mapper.UserMapper;
import com.hqshop.ecommerce.multivendor1.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserMapper userMapper;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(UserCreationRequest request){
       
        if(userRepository.existsByName(request.getName())){
            throw new AppException(Errorcode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Roles.CUSTOMER.name());
        user.setRoles(roles);
        return userRepository.save(user);
    }
    //     if(userRepository.existsByName(request.getName())){
    //         throw new AppException(Errorcode.USER_EXISTED);
    //     }
    //     User user = userMapper.toUser(request);
    //     user.setPassword(passwordEncoder.encode(user.getPassword()));

    //     HashSet<String> roles = new HashSet<>();
    //     roles.add(Roles.CUSTOMER.name());
    //     user.setRoles(roles);
    //     return userRepository.save(user);
    // }
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
    public UserResponse myInfor(){
        var contex = SecurityContextHolder.getContext();
        String name = contex.getAuthentication().getName();

        User user = userRepository.findByName(name).orElseThrow(()->
                new AppException(Errorcode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
            .stream()
            .map(userMapper::toUserResponse)
            .collect(Collectors.toList());
        }

    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found")));
    }
    public UserResponse updateUser(String userId , UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

}
