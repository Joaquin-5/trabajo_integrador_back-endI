package com.clinicaOdontologica.service;

import com.clinicaOdontologica.exceptions.BadRequestException;
import com.clinicaOdontologica.persistance.entities.Paciente;
import com.clinicaOdontologica.persistance.repository.DomicilioRepository;
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

    @Autowired
    DomicilioRepository domicilioRepository;

    private final Logger logger = Logger.getLogger(Paciente.class);

    public Paciente guardar(Paciente p) throws BadRequestException {
        if (repository.findPacienteByDNI(p.getDNI()) != null)
            throw new BadRequestException("Ya existe un paciente con este DNI: " + p.getDNI());
        p.setFechaDeAlta(new Date());
        logger.info("Se ha registrado el paciente con el siguiente nombre: " + repository.save(p).getNombre() + " y el siguiente id: " + repository.save(p).getId());
        return repository.save(p);
    }

    public List<Paciente> obtenerTodos() {
        logger.info("Obteniendo todos los pacientes...");
        return repository.findAll();
    }

    public Optional<Paciente> obtenerPorId(Long id) {
        if (repository.findById(id).isPresent()) {
            logger.info("Obteniendo el paciente con el id: " + id);
            return repository.findById(id);
        }
        return null;
    }

    public boolean modificar(Long id, Paciente p) {
        Optional<Paciente> pacienteOptional = repository.findById(id);
        if (pacienteOptional.isPresent()) {
            Paciente pacienteExistente = pacienteOptional.get();
            logger.info("Se ha modificado el pacietente con el siguiente id: " + p.getId());

            if (p.getNombre() != null) {
                pacienteExistente.setNombre(p.getNombre());
            }
            if (p.getApellido() != null) {
                pacienteExistente.setApellido(p.getApellido());
            }
            if (p.getDNI() != null) {
                pacienteExistente.setDNI(p.getDNI());
            }
            if (p.getFechaDeAlta() != null) {
                pacienteExistente.setFechaDeAlta(p.getFechaDeAlta());
            }
            if (p.getDomicilio() != null) {
                pacienteExistente.setDomicilio(p.getDomicilio());
            }
            repository.save(pacienteExistente);
            return true;
        }
        return false;
    }

    public boolean eliminar(Long id) {
        Optional<Paciente> pacienteOptional = this.obtenerPorId(id);
        if (pacienteOptional.isPresent()) {
            Paciente pacienteExistente = pacienteOptional.get();
            domicilioRepository.deleteById(pacienteExistente.getDomicilio().getId());
            repository.deleteById(id);
            return true;
        }

        return false;
    }
}
