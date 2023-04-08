package com.clinicaOdontologica;

import com.clinicaOdontologica.exceptions.BadRequestException;
import com.clinicaOdontologica.persistance.entities.Odontologo;
import com.clinicaOdontologica.service.OdontologoService;
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
public class OdontologoApplicationTest {
    @Autowired
    private OdontologoService service;

    @BeforeEach
    public void cargarDataSet() {
        Odontologo odontologo = new Odontologo("Juan", "Perez", "AF-20-31");

    }

    @Test
    public void agregar() throws BadRequestException {
        this.cargarDataSet();
        Odontologo odontologo = new Odontologo("Marcos", "Hernandez", "EL-90-WE");
        try {
            service.guardar(odontologo);
            Assert.assertTrue(odontologo != null);
        } catch (Exception e) {
            throw new BadRequestException("Ocurrió un error al agregar un odontólogo");
        }
    }

    @Test
    public void traerTodos() {
        List<Odontologo> odontologos = service.obtenerTodos();
        try {
            Assert.assertTrue(!odontologos.isEmpty());
            if (odontologos.isEmpty())
                throw new BadRequestException("No se encontraron odontólogos en la base de datos");
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void traerPorId() {
        try {
            Optional<Odontologo> odontologos = service.obtenerPorId(1L);
            if (!odontologos.isPresent()) {
                throw new BadRequestException("No se encontró ningún odontólogo con ese id");
            }
        } catch (BadRequestException e) {
            throw new RuntimeException("Ocrrió un error al traer el odontólogo" + e);
        }
    }

    @Test
    public void modificar() {
        try {
            boolean resultado = service.modificar(1L, new Odontologo("Julian", "Gonzalez", "23-RT-R4"));
            Assert.assertTrue(resultado);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrió un error al actualizar el odontólogo" + e);
        }
    }

    @Test
    public void eliminar() {
        try {
            Assert.assertTrue(service.eliminar(1L));
        } catch (Exception e) {
            throw new RuntimeException("Ocurrió un errpr al eliminar el odontólogo con ese id" + e);
        }
    }
}
