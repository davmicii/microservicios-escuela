package com.escuela.authservice.entitie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Usuario")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Usuario {
    @Id
    private Long idUsuario;
    @Column
    private String correo;
    @Column
    private String contrasenia;

    //public Usuario() {}
}
