package com.casamon.formacao.controllers.forms;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String senha;
}
