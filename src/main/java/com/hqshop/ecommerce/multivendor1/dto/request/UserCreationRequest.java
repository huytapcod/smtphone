package com.hqshop.ecommerce.multivendor1.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    @Size(min = 3, max = 50, message = "USERNAME_INVALID")
    private String name;

    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;
    private String email;
    private String phone;
    private String address;



}
