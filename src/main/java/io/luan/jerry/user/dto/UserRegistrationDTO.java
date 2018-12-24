package io.luan.jerry.user.dto;

import lombok.Data;

@Data
public class UserRegistrationDTO {

    private String username;
    private String password;
    private String confirmPassword;

}
