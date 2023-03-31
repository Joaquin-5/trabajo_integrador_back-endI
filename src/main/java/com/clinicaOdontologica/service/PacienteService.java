package com.clinicaOdontologica.service;

import com.clinicaOdontologica.persistance.entities.Paciente;
import com.clinicaOdontologica.persistance.repository.PacienteRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    PacienteRepository repository;

    private final Logger logger = Logger.getLogger(Paciente.class);

    public String guardar(Paciente p) {

        p.setFechaDeAlta(new Date());
        if (repository.save(p) != null) {
            logger.info("Se ha registrado el paciente con el siguiente nombre: " + p.getNombre() + " y el siguiente id: " + p.getId());
            return "Ok";
        }
        else return null;
    }

    public List<Paciente> obtenerTodos() {
        logger.info("Obteniendo todos los pacientes...");
        return repository.findAll();
    }

    public Optional<Paciente> obtenerPorId(Long id) {
        logger.info("Obteniendo el paciente con el id: " + id);
        return repository.findById(id);
    }

    public void modificar(Paciente p) {
        logger.info("Se ha modificado el pacietente con el siguiente id: " + p.getId());
        repository.save(p);
    }

    public void eliminar(Long id) {
        logger.info("Se ha eliminado el paciente con el siguiente id: " + id);
        repository.deleteById(id);
    }
}
