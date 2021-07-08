package com.myblog.myblogdemo.dto;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignupRequestDto {


    private String username;


    private String password;

    private String passwordconfirm;
    private String email;
    private boolean admin = false;
    private String adminToken = "";
}
