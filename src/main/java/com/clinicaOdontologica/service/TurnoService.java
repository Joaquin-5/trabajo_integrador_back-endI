package com.clinicaOdontologica.service;

import com.clinicaOdontologica.persistance.entities.Odontologo;
import com.clinicaOdontologica.persistance.entities.Paciente;
import com.clinicaOdontologica.persistance.entities.Turno;
import com.clinicaOdontologica.persistance.repository.TurnoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
    TurnoRepository repository;

    private final Logger logger = Logger.getLogger(Turno.class);

    public String guardar(Turno t) {
        if (repository.save(t) != null) {
            logger.info("Se ha registrado un turno con el siguiente id: " + t.getId());
            return "Ok";
        } else return null;
    }

    public List<Turno> obtenerTodos() {
        logger.info("Obteniendo todos los turnos...");
        return repository.findAll();
    }

    public Optional<Turno> obtenerPorId(Long id) {
        logger.info("Obteniendo un turno con el id: " + id);
        return repository.findById(id);
    }

    public void modificar(Turno t) {
        logger.info("Se ha modificado el turno con el siguiente id: " + t.getId());
        repository.save(t);
    }

    public void eliminar(Long id) {
        logger.info("Se ha eliminado el turno con el siguiente id: " + id);
        repository.deleteById(id);
    }
}
