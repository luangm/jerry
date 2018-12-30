package io.luan.jerry.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegistrationDTO implements Serializable {

    static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String confirmPassword;

}
