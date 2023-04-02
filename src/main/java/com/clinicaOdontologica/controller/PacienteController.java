package com.clinicaOdontologica.controller;

import com.clinicaOdontologica.persistance.entities.Paciente;
import com.clinicaOdontologica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    PacienteService service;

    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody Paciente p) {
        ResponseEntity<String> respuesta = null;

        if (service.guardar(p) != null) {
            respuesta = ResponseEntity.ok("El paciente fue registrado con éxito");
        } else {
            respuesta = ResponseEntity.internalServerError().body("Ocurrió un error al crear el paciente");
        }

        return respuesta;
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Paciente>> obtenerPorId(@PathVariable Long id) {
        Optional<Paciente> paciente = service.obtenerPorId(id);

        return ResponseEntity.ok(paciente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable Long id, @RequestBody Paciente p) {
        ResponseEntity<String> respuesta = null;

        if (service.obtenerPorId(id).isPresent()) {
            service.modificar(id, p);
            respuesta = ResponseEntity.ok("Paciente actualizado");
        } else {
            respuesta =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al actualizar el paciente");
        }

        return respuesta;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        ResponseEntity<String> respuesta = null;

        if (service.obtenerPorId(id).isPresent()) {
            service.eliminar(id);
            respuesta = ResponseEntity.ok("Paciente eliminado");
        } else {
            respuesta = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ocurrió un error al eliminar el paciente");
        }

        return respuesta;
    }
}
