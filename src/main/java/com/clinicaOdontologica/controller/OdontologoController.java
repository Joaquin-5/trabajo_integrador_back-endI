package com.clinicaOdontologica.controller;

import com.clinicaOdontologica.exceptions.BadRequestException;
import com.clinicaOdontologica.persistance.entities.Odontologo;
import com.clinicaOdontologica.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class OdontologoController {

    @Autowired
    OdontologoService service;

    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody Odontologo o) throws BadRequestException {
        ResponseEntity<String> respuesta = null;

        if (service.guardar(o) != null) {
            respuesta = ResponseEntity.ok("El odontólogo fue registrado con éxito");
        } else
            respuesta = ResponseEntity.badRequest().body("Ocurrió un error al registrar un odontólogo");

        return respuesta;
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> obtenerTodos() {
        ResponseEntity<List<Odontologo>> respuesta = null;
        if (service.obtenerTodos() != null)
            return ResponseEntity.status(200).body(service.obtenerTodos());

        return respuesta;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Odontologo>> obtenerPorId(@PathVariable Long id) {
        Optional<Odontologo> odontologo = service.obtenerPorId(id);

        return ResponseEntity.ok(odontologo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable Long id, @RequestBody Odontologo o) {
        ResponseEntity<String> respuesta = null;

        if (service.obtenerPorId(id).isPresent()) {
            service.modificar(id, o);
            respuesta = ResponseEntity.ok("Odontólogo actualizado con éxito");
        } else {
            respuesta = ResponseEntity.badRequest().body("Ocurrió un error al actualizar el odontólogo");
        }

        return respuesta;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        ResponseEntity<String> respuesta = null;

        if (service.obtenerPorId(id).isPresent()) {
            service.eliminar(id);
            respuesta = ResponseEntity.ok("Odontólogo eliminado con éxito");
        } else respuesta = ResponseEntity.badRequest().body("Ocurrió un error al eliminar el odontólogo");

        return respuesta;
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> procesarErrorBadRequest(BadRequestException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
