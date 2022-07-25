package com.mauro.tennis.springtennis.service;

import com.mauro.tennis.springtennis.EntrenadorServiceImpl;
import com.mauro.tennis.springtennis.dto.EntrenadorDTO;
import com.mauro.tennis.springtennis.mapper.EntrenadorMapperImpl;
import com.mauro.tennis.springtennis.model.Entrenador;
import com.mauro.tennis.springtennis.repository.EntrenadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntrenadorServiceTest {
    private final List<Entrenador> entrenadoresDePrueba = new ArrayList<>();
    private final List<EntrenadorDTO> entrenadoresDePruebaDTO = new ArrayList<>();

    private final EntrenadorDTO entrenadorParaAgregar = new EntrenadorDTO();

    EntrenadorServiceImpl entrenadorService;

    @Mock
    EntrenadorRepository entrenadorRepository;

    @BeforeEach
    public void setUp() {
        entrenadoresDePrueba.clear();
        entrenadoresDePrueba.add(new Entrenador());
        entrenadoresDePrueba.add(new Entrenador());
        entrenadoresDePrueba.add(new Entrenador());
        entrenadoresDePrueba.add(new Entrenador());

        entrenadoresDePrueba.get(0).setNombre("entrenador 1");
        entrenadoresDePrueba.get(1).setNombre("entrenador 2");
        entrenadoresDePrueba.get(2).setNombre("entrenador 3");
        entrenadoresDePrueba.get(3).setNombre("entrenador 4");
        entrenadoresDePrueba.get(0).setId(1L);
        entrenadoresDePrueba.get(1).setId(2L);
        entrenadoresDePrueba.get(2).setId(3L);
        entrenadoresDePrueba.get(3).setId(4L);

        entrenadoresDePruebaDTO.clear();
        entrenadoresDePruebaDTO.add(new EntrenadorDTO());
        entrenadoresDePruebaDTO.add(new EntrenadorDTO());
        entrenadoresDePruebaDTO.add(new EntrenadorDTO());
        entrenadoresDePruebaDTO.add(new EntrenadorDTO());

        entrenadoresDePruebaDTO.get(0).setNombre("entrenador 1");
        entrenadoresDePruebaDTO.get(1).setNombre("entrenador 2");
        entrenadoresDePruebaDTO.get(2).setNombre("entrenador 3");
        entrenadoresDePruebaDTO.get(3).setNombre("entrenador 4");
        entrenadoresDePruebaDTO.get(0).setId(1L);
        entrenadoresDePruebaDTO.get(1).setId(2L);
        entrenadoresDePruebaDTO.get(2).setId(3L);
        entrenadoresDePruebaDTO.get(3).setId(4L);

        entrenadorParaAgregar.setId(5L);
        entrenadorParaAgregar.setNombre("entrenador 5");

        entrenadorService = new EntrenadorServiceImpl(entrenadorRepository,new EntrenadorMapperImpl());
    }

    @Test
    void testListEntrenadores() {
        when(entrenadorRepository.findAll()).thenReturn(entrenadoresDePrueba);

        List<EntrenadorDTO> entrenadoresConseguidos = entrenadorService.listAll();

        assertEquals(entrenadoresDePruebaDTO.toString(), entrenadoresConseguidos.toString());

        verify(entrenadorRepository).findAll();
    }

    @Test
    void testGetEntrenadorByID() {
        when(entrenadorRepository.findById(entrenadoresDePrueba.get(0).getId()))
                .thenReturn(Optional.of(entrenadoresDePrueba.get(0)));

        EntrenadorDTO entrenadorEncontrado = entrenadorService.getById(entrenadoresDePrueba.get(0).getId());

        assertEquals(entrenadoresDePruebaDTO.get(0).getId(), entrenadorEncontrado.getId());
        assertEquals(entrenadoresDePruebaDTO.get(0).getNombre(), entrenadorEncontrado.getNombre());

        verify(entrenadorRepository).findById(eq(entrenadoresDePrueba.get(0).getId()));
    }

    @Test
    void testSaveOrUpdate() {
        ArgumentCaptor<Entrenador> argumentCaptor = ArgumentCaptor.forClass(Entrenador.class);
        when(entrenadorRepository.save(argumentCaptor.capture())).thenReturn(new Entrenador());

        entrenadorService.save(entrenadorParaAgregar);

        assertEquals(entrenadorParaAgregar.getId(),argumentCaptor.getValue().getId());
        assertEquals(entrenadorParaAgregar.getNombre(),argumentCaptor.getValue().getNombre());

        verify(entrenadorRepository).save(any(Entrenador.class));
    }

    @Test
    void testDelete() {
        Long idParaBorrar = 1L;

        when(entrenadorRepository.existsById(idParaBorrar)).thenReturn(true);

        entrenadorService.delete(idParaBorrar);

        verify(entrenadorRepository).deleteById(eq(idParaBorrar));
    }

    @Test
    void testDeleteNotFound() {
        Long idParaBorrar = 1L;
        when(entrenadorRepository.existsById(idParaBorrar)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> entrenadorService.delete(idParaBorrar));

        verify(entrenadorRepository).existsById(eq(idParaBorrar));
        verify(entrenadorRepository,times(0)).deleteById(eq(idParaBorrar));
    }

    @Test
    void testInsertExisting() {

        when(entrenadorRepository.existsById(entrenadorParaAgregar.getId())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->  entrenadorService.save(entrenadorParaAgregar));

        verify(entrenadorRepository).existsById(eq(entrenadorParaAgregar.getId()));
        verify(entrenadorRepository,times(0)).save(any());

    }

    @Test
    void testUpdateExisting() {
        ArgumentCaptor<Entrenador> argumentCaptor = ArgumentCaptor.forClass(Entrenador.class);
        when(entrenadorRepository.existsById(entrenadorParaAgregar.getId())).thenReturn(true);
        when(entrenadorRepository.save(argumentCaptor.capture())).thenReturn(new Entrenador());

        entrenadorService.update(entrenadorParaAgregar);

        assertEquals(entrenadorParaAgregar.getId(),argumentCaptor.getValue().getId());
        assertEquals(entrenadorParaAgregar.getNombre(),argumentCaptor.getValue().getNombre());

        verify(entrenadorRepository).existsById(eq(entrenadorParaAgregar.getId()));
        verify(entrenadorRepository).save(any(Entrenador.class));

    }

    @Test
    void testUpdateNotFound() {
        when(entrenadorRepository.existsById(entrenadorParaAgregar.getId())).thenReturn(false);

        assertThrows(NoSuchElementException.class, () ->  entrenadorService.update(entrenadorParaAgregar));

        verify(entrenadorRepository).existsById(eq(entrenadorParaAgregar.getId()));
        verify(entrenadorRepository,times(0)).save(any());
    }
}