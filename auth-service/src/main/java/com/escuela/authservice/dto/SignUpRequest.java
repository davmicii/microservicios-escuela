package com.escuela.authservice.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String correo;
    private String contrasenia;
}
