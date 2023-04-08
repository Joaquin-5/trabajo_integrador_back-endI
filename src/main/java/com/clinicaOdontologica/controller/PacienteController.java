package com.clinicaOdontologica.controller;

import com.clinicaOdontologica.exceptions.BadRequestException;
import com.clinicaOdontologica.persistance.entities.Paciente;
import com.clinicaOdontologica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class PacienteController {

    @Autowired
    PacienteService service;

    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody Paciente p) throws BadRequestException {
        ResponseEntity<String> respuesta = null;

        if (service.guardar(p) != null) {
            respuesta = ResponseEntity.ok("El paciente fue registrado con éxito");
        } else
            respuesta = ResponseEntity.badRequest().body("Ocurrió un error al crear el paciente");

        return respuesta;
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> obtenerTodos() {
        ResponseEntity<List<Paciente>> respuesta = null;
        if (!service.obtenerTodos().isEmpty())
            return respuesta.ok(service.obtenerTodos());

        return respuesta;
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
        } else
            respuesta = ResponseEntity.badRequest().body("Ocurrió un error al actualizar el paciente");

        return respuesta;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        ResponseEntity<String> respuesta = null;

        if (service.obtenerPorId(id).isPresent()) {
            service.eliminar(id);
            respuesta = ResponseEntity.ok("Paciente eliminado");
        } else respuesta = ResponseEntity.badRequest().body("Ocurrió un error al eliminar el paciente");

        return respuesta;
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> procesarErrorBadRequest(BadRequestException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
