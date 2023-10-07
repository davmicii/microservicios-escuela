package com.escuela.materiamicroservice.controller;

import com.escuela.materiamicroservice.entitie.Materia;
import com.escuela.materiamicroservice.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/materia")
public class MateriaController {
    private final MateriaService materiaService;

    @Autowired
    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @PostMapping("/insertar")
    public ResponseEntity<String> insertarMateria(@RequestBody Materia materia) {
        try {
            materiaService.createMateria(materia);
            return ResponseEntity.ok("Materia insertada correctamente");
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Error al insertar materia: " + e.getMessage());
        }
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<String> modificarMateria(@PathVariable Long id, @RequestBody Materia materia) {
        materia.setIdMateria(id);
        try {
            materiaService.updateMateria(materia);
            return ResponseEntity.ok("Materia actualizada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la materia: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{idMateria}")
    public ResponseEntity<String> eliminarMateria(@PathVariable int idMateria) {
        try {
            materiaService.deleteMateria(idMateria);
            return ResponseEntity.ok("Materia eliminada correctamente");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar materia");
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Materia>> listarMaestros() {
        List<Materia> maestros = materiaService.listarMaterias();
        return new ResponseEntity<>(maestros, HttpStatus.OK);
    }
}
