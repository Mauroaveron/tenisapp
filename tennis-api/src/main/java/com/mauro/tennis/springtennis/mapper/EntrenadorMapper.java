package com.mauro.tennis.springtennis.mapper;

import com.mauro.tennis.springtennis.dto.CanchaDTO;
import com.mauro.tennis.springtennis.dto.EntrenadorDTO;
import com.mauro.tennis.springtennis.model.Cancha;
import com.mauro.tennis.springtennis.model.Entrenador;

public interface EntrenadorMapper {

    EntrenadorDTO toDTO(Entrenador entity);
    Entrenador fromDTO(EntrenadorDTO entity);

}