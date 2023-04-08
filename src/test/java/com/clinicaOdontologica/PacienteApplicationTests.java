package com.clinicaOdontologica;

import com.clinicaOdontologica.exceptions.BadRequestException;
import com.clinicaOdontologica.persistance.entities.Domicilio;
import com.clinicaOdontologica.persistance.entities.Paciente;
import com.clinicaOdontologica.service.PacienteService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
class PacienteApplicationTests {

    @Autowired
    private PacienteService service;

    @BeforeEach
    public void cargarDataSet() {
        Domicilio domicilio = new Domicilio("Achával", "956", "Parque Chacabuco", "Buenos Aires");

        Paciente paciente = new Paciente("Nicolas", "Martinez", domicilio, "12345678");

    }

    @Test
    public void agregar() throws BadRequestException {
        this.cargarDataSet();
        Domicilio domicilio = new Domicilio("Achával", "956", "Parque Chacabuco", "Buenos Aires");
        Paciente paciente = new Paciente("Carlos", "Gutierrez", domicilio, "54897621");
        try {
            service.guardar(paciente);
            Assert.assertTrue(service.obtenerPorId(1L) != null);
        } catch (Exception e) {
            throw  new BadRequestException("Ocurrió un error al crear el paciente");
        }
        Assert.assertTrue(paciente.getId() != null);
    }

    @Test
    public void traerTodos() {
        List<Paciente> pacientes = service.obtenerTodos();
        try {
            Assert.assertTrue(!pacientes.isEmpty());
            if (pacientes.isEmpty())
                throw new BadRequestException("No se encontraron pacientes en la base de datos");
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void traerPorId() {
        try {
            Optional<Paciente> paciente = service.obtenerPorId(1L);
            Assert.assertTrue(paciente != null);
            if (!paciente.isPresent()) {
                throw new BadRequestException("No se encontró ningún paciente");
            }
        } catch (BadRequestException e) {
            throw new RuntimeException("Ocurrió un error al traer el paciente " + e);
        }

    }

    @Test
    public void modificar() {
        try {
            Domicilio domicilio = new Domicilio("alvear", "654", "San isidro", "Buenos Aires");
            Paciente paciente = new Paciente("Santos", "Ramirez", domicilio, "14598763");
            boolean resultado = service.modificar(1L, paciente);
            Assert.assertTrue(resultado);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrió un error al modificar el paciente " + e);
        }
    }

    @Test
    public void eliminar() {
        try {
            Assert.assertTrue(service.eliminar(1L));
        } catch (Exception e) {
            throw new RuntimeException("Ocurrió un error al eliminar el paciente" + e);
        }
    }
}
