package com.clinicaOdontologica.service;

import com.clinicaOdontologica.exceptions.BadRequestException;
import com.clinicaOdontologica.persistance.entities.Odontologo;
import com.clinicaOdontologica.persistance.repository.OdontologoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    @Autowired
    OdontologoRepository repository;

    private final Logger logger = Logger.getLogger(Odontologo.class);

    public Odontologo guardar(Odontologo o) throws BadRequestException {
        if (repository.findOdontologoByMatricula(o.getMatricula()) != null)
            throw new BadRequestException("Ya existe un odontólogo con esta matrícula: " + o.getMatricula());
        logger.info("Se ha registrado el odontólogo con el siguiente nombre: " + repository.save(o).getNombre() + " y el siguiente id: " + repository.save(o).getId());
        return repository.save(o);
    }

    public List<Odontologo> obtenerTodos() {
        logger.info("Obteniendo todos los odontólogos...");
        return repository.findAll();
    }

    public Optional<Odontologo> obtenerPorId(Long id) {
        if (repository.findById(id).isPresent()) {
            logger.info("Obteniendo el odontólogo con el id: " + id);
            return repository.findById(id);
        }
        return null;
    }

    public boolean modificar(Long id, Odontologo o) {
        Optional<Odontologo> odontologoOptional = repository.findById(id);
        if (odontologoOptional.isPresent()){
            Odontologo odontologoExistente = odontologoOptional.get();
            logger.info("Se ha modificado el pacietente con el siguiente id: " + o.getId());

            if (o.getNombre() != null){
                odontologoExistente.setNombre(o.getNombre());
            }
            if(o.getApellido() != null){
                odontologoExistente.setApellido(o.getApellido());
            }
            if(o.getMatricula() != null){
                odontologoExistente.setMatricula(o.getMatricula());
            }
            repository.save(odontologoExistente);
            return true;
        }
        return false;
    }

    public boolean eliminar(Long id) {
        if (repository.findById(id).isPresent()) {
            logger.info("Se ha eliminado el odontólogo con el siguiente id: " + id);
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
