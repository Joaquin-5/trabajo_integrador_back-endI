package com.clinicaOdontologica.service;

import com.clinicaOdontologica.persistance.entities.Odontologo;
import com.clinicaOdontologica.persistance.entities.Paciente;
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

    public Odontologo guardar(Odontologo o) {
        logger.info("Se ha registrado el odontólogo con el siguiente nombre: " + o.getNombre() + " y el siguiente id: " + o.getId());
        return repository.save(o);
    }

    public List<Odontologo> obtenerTodos() {
        logger.info("Obteniendo todos los odontólogos...");
        return repository.findAll();
    }

    public Optional<Odontologo> obtenerPorId(Long id) {
        logger.info("Obteniendo el odontólogo con el id: " + id);
        return repository.findById(id);
    }

    public void modificar(Long id, Odontologo o) {
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
        }
    }

    public void eliminar(Long id) {
        logger.info("Se ha eliminado el odontólogo con el siguiente id: " + id);
        repository.deleteById(id);
    }
}
