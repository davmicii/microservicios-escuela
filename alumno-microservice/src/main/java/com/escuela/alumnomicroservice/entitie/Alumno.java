package com.escuela.alumnomicroservice.entitie;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Alumnos")
public class Alumno {
    @Id
    private Long idAlumno;
    @Column("nombre")
    private String nombre;
    @Column("apellido")
    private String apellido;

    @Column("Maestro_Id")
    private Long Maestro_Id;

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
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

    public Long getMaestro_Id() {
        return Maestro_Id;
    }

    public void setMaestro_Id(Long maestro_Id) {
        this.Maestro_Id = maestro_Id;
    }
}
