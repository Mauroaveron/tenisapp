package com.mauro.tennis.springtennis.repository;

import com.mauro.tennis.springtennis.model.Cancha;
import com.mauro.tennis.springtennis.model.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {

}
