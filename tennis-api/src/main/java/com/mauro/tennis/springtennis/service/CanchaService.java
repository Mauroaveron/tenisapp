package com.mauro.tennis.springtennis.service;

import com.mauro.tennis.springtennis.dto.CanchaDTO;
import com.mauro.tennis.springtennis.model.Cancha;

import java.util.List;

public interface CanchaService {
	List<CanchaDTO> listAll();

	CanchaDTO getById(Long id);

	CanchaDTO save(CanchaDTO cancha);

	CanchaDTO update(CanchaDTO cancha);

	void delete(Long id);

}
