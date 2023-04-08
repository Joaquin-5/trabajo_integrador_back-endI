package com.clinicaOdontologica.persistance.repository;

import com.clinicaOdontologica.persistance.entities.Domicilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomicilioRepository extends JpaRepository<Domicilio, Long> {
}
