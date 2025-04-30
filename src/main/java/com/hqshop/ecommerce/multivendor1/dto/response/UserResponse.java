package com.hqshop.ecommerce.multivendor1.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    private String name;
    private String email;
    private String phone;
    private String address;
    Set<String> roles;
}
