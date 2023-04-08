package com.clinicaOdontologica.service;

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

    public Turno guardar(Turno t) {
        logger.info("Se ha registrado un turno con el siguiente id: " + t.getId());
        return repository.save(t);
    }

    public List<Turno> obtenerTodos() {
        logger.info("Obteniendo todos los turnos...");
        return repository.findAll();
    }

    public Optional<Turno> obtenerPorId(Long id) {
        if (repository.findById(id).isPresent()) {
            logger.info("Se obtuvo un turno con el id: " + id);
            return repository.findById(id);
        }
        return null;
    }

    public boolean modificar(Long id, Turno t) {
        Optional<Turno> turnoOptional = repository.findById(id);
        if (turnoOptional.isPresent()){
            Turno turnoExistente = turnoOptional.get();
            logger.info("Se ha modificado el turno con el siguiente id: " + t.getId());

            if (t.getFecha() != null){
                turnoExistente.setFecha(t.getFecha());
            }
            if(t.getPaciente() != null){
                turnoExistente.setPaciente(t.getPaciente());
            }
            if(t.getOdontologo() != null){
                turnoExistente.setOdontologo(t.getOdontologo());
            }
            repository.save(turnoExistente);
            return true;
        }
        return false;
    }

    public boolean eliminar(Long id) {
        if (repository.findById(id).isPresent()) {
            logger.info("Se ha eliminado el turno con el siguiente id: " + id);
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
