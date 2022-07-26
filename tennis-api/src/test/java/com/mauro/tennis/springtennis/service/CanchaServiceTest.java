package com.mauro.tennis.springtennis.service;

import com.mauro.tennis.springtennis.dto.CanchaDTO;
import com.mauro.tennis.springtennis.mapper.CanchaMapper;
import com.mauro.tennis.springtennis.mapper.CanchaMapperImpl;
import com.mauro.tennis.springtennis.model.Cancha;
import com.mauro.tennis.springtennis.repository.CanchaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
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
class CanchaServiceTest {
    private final List<Cancha> canchasDePrueba = new ArrayList<>();
    private final List<CanchaDTO> canchasDePruebaDTO = new ArrayList<>();

    private final CanchaDTO canchaParaAgregar = new CanchaDTO();

    CanchaServiceImpl canchaService;

    @Mock
    CanchaRepository canchaRepository;

    @BeforeEach
    public void setUp() {
        canchasDePrueba.clear();
        canchasDePrueba.add(new Cancha());
        canchasDePrueba.add(new Cancha());
        canchasDePrueba.add(new Cancha());
        canchasDePrueba.add(new Cancha());

        canchasDePrueba.get(0).setNombre("cancha 1");
        canchasDePrueba.get(1).setNombre("cancha 2");
        canchasDePrueba.get(2).setNombre("cancha 3");
        canchasDePrueba.get(3).setNombre("cancha 4");
        canchasDePrueba.get(0).setId(1L);
        canchasDePrueba.get(1).setId(2L);
        canchasDePrueba.get(2).setId(3L);
        canchasDePrueba.get(3).setId(4L);
        canchasDePrueba.get(0).setDireccion("av random 123");
        canchasDePrueba.get(1).setDireccion("calle rgn 321");
        canchasDePrueba.get(2).setDireccion("calle inventada 777");
        canchasDePrueba.get(3).setDireccion("av siempreviva 156");

        canchasDePruebaDTO.clear();
        canchasDePruebaDTO.add(new CanchaDTO());
        canchasDePruebaDTO.add(new CanchaDTO());
        canchasDePruebaDTO.add(new CanchaDTO());
        canchasDePruebaDTO.add(new CanchaDTO());

        canchasDePruebaDTO.get(0).setNombre("cancha 1");
        canchasDePruebaDTO.get(1).setNombre("cancha 2");
        canchasDePruebaDTO.get(2).setNombre("cancha 3");
        canchasDePruebaDTO.get(3).setNombre("cancha 4");
        canchasDePruebaDTO.get(0).setId(1L);
        canchasDePruebaDTO.get(1).setId(2L);
        canchasDePruebaDTO.get(2).setId(3L);
        canchasDePruebaDTO.get(3).setId(4L);
        canchasDePruebaDTO.get(0).setDireccion("av random 123");
        canchasDePruebaDTO.get(1).setDireccion("calle rgn 321");
        canchasDePruebaDTO.get(2).setDireccion("calle inventada 777");
        canchasDePruebaDTO.get(3).setDireccion("av siempreviva 156");

        canchaParaAgregar.setId(5L);
        canchaParaAgregar.setNombre("cancha 5");
        canchaParaAgregar.setDireccion("calle sin nombre magica");

        canchaService = new CanchaServiceImpl(canchaRepository,new CanchaMapperImpl());
    }

    @Test
    void testListCanchas() {
        when(canchaRepository.findAll()).thenReturn(canchasDePrueba);

        List<CanchaDTO> canchasConseguidos = canchaService.listAll();

        assertEquals(canchasDePruebaDTO.toString(), canchasConseguidos.toString());

        verify(canchaRepository).findAll();
    }

    @Test
    void testGetCanchaByID() {
        when(canchaRepository.findById(canchasDePrueba.get(0).getId()))
                .thenReturn(Optional.of(canchasDePrueba.get(0)));

        CanchaDTO canchaEncontrado = canchaService.getById(canchasDePrueba.get(0).getId());

        assertEquals(canchasDePruebaDTO.get(0).getId(), canchaEncontrado.getId());
        assertEquals(canchasDePruebaDTO.get(0).getDireccion(), canchaEncontrado.getDireccion());
        assertEquals(canchasDePruebaDTO.get(0).getNombre(), canchaEncontrado.getNombre());

        verify(canchaRepository).findById(eq(canchasDePrueba.get(0).getId()));
    }

    @Test
    void testSaveOrUpdate() {
        ArgumentCaptor<Cancha> argumentCaptor = ArgumentCaptor.forClass(Cancha.class);
        when(canchaRepository.save(argumentCaptor.capture())).thenReturn(new Cancha());

        canchaService.save(canchaParaAgregar);

        assertEquals(canchaParaAgregar.getId(),argumentCaptor.getValue().getId());
        assertEquals(canchaParaAgregar.getDireccion(),argumentCaptor.getValue().getDireccion());
        assertEquals(canchaParaAgregar.getNombre(),argumentCaptor.getValue().getNombre());

        verify(canchaRepository).save(any(Cancha.class));
    }

    @Test
    void testDelete() {
        Long idParaBorrar = 1L;

        when(canchaRepository.existsById(idParaBorrar)).thenReturn(true);

        canchaService.delete(idParaBorrar);

        verify(canchaRepository).deleteById(eq(idParaBorrar));
    }

    @Test
    void testDeleteNotFound() {
        Long idParaBorrar = 1L;
        when(canchaRepository.existsById(idParaBorrar)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> canchaService.delete(idParaBorrar));

        verify(canchaRepository).existsById(eq(idParaBorrar));
        verify(canchaRepository,times(0)).deleteById(eq(idParaBorrar));
    }

    @Test
    void testInsertExisting() {

        when(canchaRepository.existsById(canchaParaAgregar.getId())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->  canchaService.save(canchaParaAgregar));

        verify(canchaRepository).existsById(eq(canchaParaAgregar.getId()));
        verify(canchaRepository,times(0)).save(any());

    }

    @Test
    void testUpdateExisting() {
        ArgumentCaptor<Cancha> argumentCaptor = ArgumentCaptor.forClass(Cancha.class);
        when(canchaRepository.existsById(canchaParaAgregar.getId())).thenReturn(true);
        when(canchaRepository.save(argumentCaptor.capture())).thenReturn(new Cancha());

        canchaService.update(canchaParaAgregar);

        assertEquals(canchaParaAgregar.getId(),argumentCaptor.getValue().getId());
        assertEquals(canchaParaAgregar.getDireccion(),argumentCaptor.getValue().getDireccion());
        assertEquals(canchaParaAgregar.getNombre(),argumentCaptor.getValue().getNombre());

        verify(canchaRepository).existsById(eq(canchaParaAgregar.getId()));
        verify(canchaRepository).save(any(Cancha.class));

    }

    @Test
    void testUpdateNotFound() {
        when(canchaRepository.existsById(canchaParaAgregar.getId())).thenReturn(false);

        assertThrows(NoSuchElementException.class, () ->  canchaService.update(canchaParaAgregar));

        verify(canchaRepository).existsById(eq(canchaParaAgregar.getId()));
        verify(canchaRepository,times(0)).save(any());
    }
}