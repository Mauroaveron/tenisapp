package com.mauro.tennis.springtennis.mapper;

import com.mauro.tennis.springtennis.dto.CanchaDTO;
import com.mauro.tennis.springtennis.model.Cancha;

public interface CanchaMapper {

    CanchaDTO toDTO(Cancha entity);
    Cancha fromDTO(CanchaDTO entity);

}
