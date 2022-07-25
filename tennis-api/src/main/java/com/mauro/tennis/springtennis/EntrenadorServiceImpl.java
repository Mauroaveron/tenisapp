package com.mauro.tennis.springtennis;

import com.mauro.tennis.springtennis.dto.EntrenadorDTO;
import com.mauro.tennis.springtennis.mapper.EntrenadorMapper;
import com.mauro.tennis.springtennis.repository.EntrenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EntrenadorServiceImpl implements EntrenadorService {

    public static final String TRAINER_WITH_ID = "Trainer with id = ";
    public static final String DOES_NOT_EXIST = " does not exist.";
    public static final String ALREADY_EXISTS = " already exists.";
    private final EntrenadorRepository entrenadorRepository;
    private final EntrenadorMapper entrenadorMapper;

    @Autowired
    public EntrenadorServiceImpl(EntrenadorRepository entrenadorRepository, EntrenadorMapper entrenadorMapper) {
        this.entrenadorRepository = entrenadorRepository;
        this.entrenadorMapper = entrenadorMapper;
    }

    @Override
    public List<EntrenadorDTO> listAll() {
        return entrenadorRepository.findAll()
                .stream()
                .map(this.entrenadorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EntrenadorDTO getById(Long id) {
        return entrenadorMapper.toDTO(entrenadorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(TRAINER_WITH_ID + id + DOES_NOT_EXIST)));
    }

    @Override
    public EntrenadorDTO save(EntrenadorDTO entrenador) {
        boolean exists = entrenador.getId() != null && entrenadorRepository.existsById(entrenador.getId());
        if (exists) {
            throw new IllegalArgumentException(TRAINER_WITH_ID + entrenador.getId() + ALREADY_EXISTS);
        }
        return this.entrenadorMapper
                .toDTO(
                        entrenadorRepository.save(this.entrenadorMapper.fromDTO(entrenador)));
    }

    @Override
    public EntrenadorDTO update(EntrenadorDTO entrenador) {
        boolean exists = entrenadorRepository.existsById(entrenador.getId());
        if (!exists) {
            throw new NoSuchElementException(TRAINER_WITH_ID + entrenador.getId() + DOES_NOT_EXIST);
        }
        return this.entrenadorMapper
                .toDTO(
                        entrenadorRepository.save(this.entrenadorMapper.fromDTO(entrenador)));
    }

    @Override
    public void delete(Long id) {
        boolean exists = entrenadorRepository.existsById(id);
        if (!exists) {
            throw new NoSuchElementException(TRAINER_WITH_ID + id + DOES_NOT_EXIST);
        }
        entrenadorRepository.deleteById(id);
    }

}