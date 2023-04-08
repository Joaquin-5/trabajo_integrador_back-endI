package com.clinicaOdontologica.persistance.repository;

import com.clinicaOdontologica.persistance.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Paciente findPacienteByDNI(String DNI);
}
