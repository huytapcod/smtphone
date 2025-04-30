package com.hqshop.ecommerce.multivendor1.controller;

import com.hqshop.ecommerce.multivendor1.dto.request.ApiResponse;
import com.hqshop.ecommerce.multivendor1.dto.request.UserCreationRequest;
import com.hqshop.ecommerce.multivendor1.dto.request.UserUpdateRequest;
import com.hqshop.ecommerce.multivendor1.dto.response.UserResponse;
import com.hqshop.ecommerce.multivendor1.entity.User;
import com.hqshop.ecommerce.multivendor1.repository.UserRepository;
import com.hqshop.ecommerce.multivendor1.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private UserResponse userResponse;

    @PostMapping
    ApiResponse<User> ceationUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<User> apiReponse = new ApiResponse<>();
        apiReponse.setResult(userService.createUser(request));
        return apiReponse;
    }
    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        var securityContext = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}" , securityContext.getName());
        securityContext.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build();
    }
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/myInfor")
    ApiResponse<UserResponse> getMyInfor(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }
//    @DeleteMapping("{userId}")
//    UserResponse deleteUser(@PathVariable String userId, @RequestBody U) {}
}
