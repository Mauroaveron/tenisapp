package com.mauro.tennis.springtennis.repository;

import com.mauro.tennis.springtennis.model.Cancha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CanchaRepository extends JpaRepository<Cancha, Long> {

}
