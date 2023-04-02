package com.clinicaOdontologica.controller;

import com.clinicaOdontologica.persistance.entities.Odontologo;
import com.clinicaOdontologica.persistance.entities.Turno;
import com.clinicaOdontologica.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    @Autowired
    TurnoService service;

    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody Turno t) {
        ResponseEntity<String> respuesta = null;

        if (service.guardar(t) != null) {
            respuesta = ResponseEntity.ok("El turno fue creado con éxito");
        } else {
            respuesta = ResponseEntity.internalServerError().body("Ocurrió un error al crear el turno");
        }

        return respuesta;
    }

    @GetMapping
    public ResponseEntity<List<Turno>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")

    public ResponseEntity<Optional<Turno>> obtenerPorId(@PathVariable Long id) {
        Optional<Turno> odontologo = service.obtenerPorId(id);

        return ResponseEntity.ok(odontologo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable Long id, @RequestBody Turno t) {
        ResponseEntity<String> respuesta = null;

        if (service.obtenerPorId(id).isPresent()) {
            service.modificar(id, t);
            respuesta = ResponseEntity.ok("Turno actualizado");
        } else {
            respuesta = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ocurrió un error al actualizar el turno");
        }

        return respuesta;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        ResponseEntity<String> respuesta = null;

        if (service.obtenerPorId(id).isPresent()) {
            service.eliminar(id);
            respuesta = ResponseEntity.ok("Turno eliminado");
        } else {
            respuesta = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ocurrió un error al eliminar el turno");
        }

        return respuesta;
    }
}
