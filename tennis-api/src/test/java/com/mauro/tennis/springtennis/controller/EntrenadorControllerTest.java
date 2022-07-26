package com.mauro.tennis.springtennis.controller;

import com.mauro.tennis.springtennis.dto.EntrenadorDTO;
import com.mauro.tennis.springtennis.service.EntrenadorServiceImpl;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(EntrenadorController.class)
class EntrenadorControllerTest {
    String basePath = "/springtennis/api/v1/entrenadores/";
    List<EntrenadorDTO> entrenadoresDePrueba = new ArrayList<>();
    JSONArray entrenadoresDePruebaEnJSON = new JSONArray();
    EntrenadorDTO entrenadorParaAgregar = new EntrenadorDTO();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EntrenadorController entrenadorController;

    @MockBean
    EntrenadorServiceImpl entrenadorService;

    @BeforeEach
    public void setUp() {
        entrenadoresDePrueba.clear();
        entrenadoresDePrueba.add(new EntrenadorDTO());
        entrenadoresDePrueba.add(new EntrenadorDTO());
        entrenadoresDePrueba.add(new EntrenadorDTO());
        entrenadoresDePrueba.add(new EntrenadorDTO());

        entrenadoresDePrueba.get(0).setNombre("entrenador 1");
        entrenadoresDePrueba.get(1).setNombre("entrenador 2");
        entrenadoresDePrueba.get(2).setNombre("entrenador 3");
        entrenadoresDePrueba.get(3).setNombre("entrenador 4");
        entrenadoresDePrueba.get(0).setId(1L);
        entrenadoresDePrueba.get(1).setId(2L);
        entrenadoresDePrueba.get(2).setId(3L);
        entrenadoresDePrueba.get(3).setId(4L);

        entrenadorParaAgregar.setId(5L);
        entrenadorParaAgregar.setNombre("entrenador 5");

        entrenadoresDePrueba.forEach((x) -> entrenadoresDePruebaEnJSON.put(x.toJSONObject()));

    }

    @Test
    void testListAll() throws Exception {
        when(entrenadorService.listAll()).thenReturn(entrenadoresDePrueba);

        mockMvc.perform(MockMvcRequestBuilders.get(basePath).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(entrenadoresDePruebaEnJSON.toString()));

        verify(entrenadorService).listAll();

    }

    @Test
    void testGetByID() throws Exception {
        long idEntrenadorGet = 1L;
        when(entrenadorService.getById(1L)).thenReturn(entrenadoresDePrueba.get(0));

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + idEntrenadorGet).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(entrenadoresDePrueba.get(0)
                        .toJSONObject().toString()));

        verify(entrenadorService).getById(eq(1L));

    }

    @Test
    void testSaveEntrenador() throws Exception {
        ArgumentCaptor<EntrenadorDTO> argumentCaptor = ArgumentCaptor.forClass(EntrenadorDTO.class);

        when(entrenadorService.save(argumentCaptor.capture())).thenReturn(new EntrenadorDTO());

        mockMvc.perform(MockMvcRequestBuilders.post(basePath).contentType(MediaType.APPLICATION_JSON)
                        .content(entrenadorParaAgregar.toJSONObject().toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        assertEquals(entrenadorParaAgregar.getNombre(), argumentCaptor.getValue().getNombre());
        verify(entrenadorService).save(any(EntrenadorDTO.class));
    }


    @Test
    void testUpdateEntrenador() throws Exception {
        ArgumentCaptor<EntrenadorDTO> argumentCaptor = ArgumentCaptor.forClass(EntrenadorDTO.class);
        when(entrenadorService.update(argumentCaptor.capture())).thenReturn(new EntrenadorDTO());

        mockMvc.perform(MockMvcRequestBuilders.put(basePath+ entrenadoresDePrueba.get(0).getId()).contentType(MediaType.APPLICATION_JSON)
                        .content(entrenadorParaAgregar.toJSONObject().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals(entrenadoresDePrueba.get(0).getId(), argumentCaptor.getValue().getId());
        assertEquals(entrenadorParaAgregar.getNombre(), argumentCaptor.getValue().getNombre());
        verify(entrenadorService).update(any(EntrenadorDTO.class));
    }

    @Test
    void testDeleteEntrenador() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(basePath+ entrenadoresDePrueba.get(0).getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(entrenadorService).delete(eq(entrenadoresDePrueba.get(0).getId()));
    }
}