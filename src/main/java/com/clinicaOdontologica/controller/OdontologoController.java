package com.clinicaOdontologica.controller;

import com.clinicaOdontologica.persistance.entities.Odontologo;
import com.clinicaOdontologica.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    @Autowired
    OdontologoService service;

    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody Odontologo o) {
        ResponseEntity<String> respuesta = null;

        if (service.guardar(o) != null) {
            respuesta = ResponseEntity.ok("El odontólogo fue registrado con éxito");
        } else {
            respuesta = ResponseEntity.internalServerError().body("Ocurrió un error al registrar un odontólogo");
        }

        return respuesta;
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
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
            respuesta = ResponseEntity.ok("Odontólogo actualizado");
        } else {
            respuesta = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ocurrió un error al actualizar el odontólogo");
        }

        return respuesta;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        ResponseEntity<String> respuesta = null;

        if (service.obtenerPorId(id).isPresent()) {
            service.eliminar(id);
            respuesta = ResponseEntity.ok("Odontólogo eliminado");
        } else {
            respuesta = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ocurrió un error al eliminar el odontólogo");
        }

        return respuesta;
    }
}
