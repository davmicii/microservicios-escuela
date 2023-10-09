package com.escuela.authservice.dto;

import lombok.Data;

@Data
public class SignInRequest {
    private String correo;
    private String contrasenia;
}
