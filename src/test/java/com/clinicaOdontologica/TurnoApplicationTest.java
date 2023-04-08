package com.clinicaOdontologica;

import com.clinicaOdontologica.exceptions.BadRequestException;
import com.clinicaOdontologica.persistance.entities.Domicilio;
import com.clinicaOdontologica.persistance.entities.Odontologo;
import com.clinicaOdontologica.persistance.entities.Paciente;
import com.clinicaOdontologica.persistance.entities.Turno;
import com.clinicaOdontologica.service.OdontologoService;
import com.clinicaOdontologica.service.PacienteService;
import com.clinicaOdontologica.service.TurnoService;
import org.junit.Assert;
import org.junit.FixMethodOrder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
public class TurnoApplicationTest {
    @Autowired
    private TurnoService service;

    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private PacienteService pacienteService;

    @BeforeEach
    public void caragarDataSet() throws BadRequestException {

        Domicilio domicilio = new Domicilio("Achával", "956", "Parque Chacabuco", "Buenos Aires");

        Paciente paciente = new Paciente("Nicolas", "Martinez", domicilio, "14587963");
        pacienteService.guardar(paciente);

        Odontologo odontologo = new Odontologo("Juan", "Perez", "RT-OP87");
        odontologoService.guardar(odontologo);
    }

    @Test
    public void agregar() throws BadRequestException {
        this.caragarDataSet();
        Turno turno = new Turno(LocalDateTime.of(2023, 5, 15, 12, 0, 0), odontologoService.obtenerPorId(1L).get(), pacienteService.obtenerPorId(1L).get());
        try {
            service.guardar(turno);
            Assert.assertTrue(service.obtenerPorId(1L) != null);
        } catch (Exception e) {
            throw new BadRequestException("Ocurrió un error al agregar el turno");
        }
    }

    @Test
    public void traerTodos() {
        List<Turno> turnos = service.obtenerTodos();
        try {
            Assert.assertTrue(!turnos.isEmpty());
            if (turnos.isEmpty()) {
                throw new BadRequestException("No se encontraron turnos en la base de datos");
            }
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void traerPorId() {
        try {
            Optional<Turno> turnos = service.obtenerPorId(1L);
            if (!turnos.isPresent()) {
                throw new BadRequestException("No se encontró ningún turno");
            }
        } catch (BadRequestException e) {
            throw new RuntimeException("Ocurrió un error al traer el turno " + e);
        }
    }

    @Test
    public void modificar() {
        try {
            boolean resultado = service.modificar(1L, new Turno(LocalDateTime.of(2023, 5, 15, 11, 0, 0), odontologoService.obtenerPorId(2L).get(), pacienteService.obtenerPorId(2L).get()));
            Assert.assertTrue(resultado);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrió un error al actualizar el turno" + e);
        }
    }

    @Test
    public void eliminar() {
        try {
            Assert.assertTrue(service.eliminar(1L));
        } catch (Exception e) {
            throw new RuntimeException("Ocurrió un error al eliminar el turno" + e);
        }
    }

}
