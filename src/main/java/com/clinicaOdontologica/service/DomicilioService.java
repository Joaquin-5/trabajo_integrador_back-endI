package com.clinicaOdontologica.service;

import com.clinicaOdontologica.persistance.entities.Domicilio;
import com.clinicaOdontologica.persistance.repository.DomicilioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomicilioService {
    @Autowired
    DomicilioRepository repository;

    public void guardar(Domicilio d) {
        repository.save(d);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
