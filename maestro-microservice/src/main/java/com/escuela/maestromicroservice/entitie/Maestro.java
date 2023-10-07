package com.escuela.maestromicroservice.entitie;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Maestro")
public class Maestro {
    public Maestro(){}

    @Id
    private Long idMaestro;

    @Column
    private String nombre;
    @Column String apellido;
    @Column String titulo;

    public Long getIdMaestro() {
        return idMaestro;
    }

    public void setIdMaestro(Long idMaestro) {
        this.idMaestro = idMaestro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
