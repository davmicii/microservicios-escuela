package com.escuela.maestromicroservice.controller;

import com.escuela.maestromicroservice.entitie.Maestro;
import com.escuela.maestromicroservice.service.MaestroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/maestro")
public class MaestroController {
    private final MaestroService maestroService;

    @Autowired
    public MaestroController(MaestroService maestroService) {
        this.maestroService = maestroService;
    }

    @PostMapping("/insertar")
    public ResponseEntity<String> insertarMaestro(@RequestBody Maestro maestro) {
        try {
            maestroService.createMaestro(maestro);
            return ResponseEntity.ok("Maestro insertado correctamente");
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Error al insertar maestro: " + e.getMessage());
        }
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<String> modificarMaestro(@PathVariable Long id, @RequestBody Maestro maestro) {
        maestro.setIdMaestro(id);
        try {
            maestroService.updateMaestro(maestro);
            return ResponseEntity.ok("Maestro actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el maestro: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{idMaestro}")
    public ResponseEntity<String> eliminarMaestro(@PathVariable int idMaestro) {
        try {
            maestroService.deleteMaestro(idMaestro);
            return ResponseEntity.ok("Maestro eliminado correctamente");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el maestro");
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Maestro>> listarMaestros() {
        List<Maestro> maestros = maestroService.listarMaestros();
        return new ResponseEntity<>(maestros, HttpStatus.OK);
    }
}
