package com.mauro.tennis.springtennis;

import com.mauro.tennis.springtennis.dto.EntrenadorDTO;
import com.mauro.tennis.springtennis.model.Entrenador;

import java.util.List;

public interface EntrenadorService {
    List<EntrenadorDTO> listAll();

    EntrenadorDTO getById(Long id);

    EntrenadorDTO save(EntrenadorDTO entrenador);

    EntrenadorDTO update(EntrenadorDTO entrenador);

    void delete(Long id);

}
