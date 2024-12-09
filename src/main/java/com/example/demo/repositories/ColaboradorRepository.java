package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import com.example.demo.models.Persona.PersonaHumana;
import com.example.demo.models.Rol.Colaborador;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {
    Colaborador findByPersona(PersonaHumana persona);

    @Query("SELECT c FROM Colaborador c JOIN FETCH c.persona p JOIN FETCH p.mediosContacto WHERE c.id = :id")
    Optional<Colaborador> findByIdWithContacts(Long id);

    Optional<Colaborador> findByUUID(String UUID);

    @Query("SELECT c FROM Colaborador c JOIN FETCH c.persona p WHERE TYPE(p) = PersonaHumana")
    List<Colaborador> findColaboradoresWithPersonasHumanas();
}