package com.mauro.tennis.springtennis.mapper;

import com.mauro.tennis.springtennis.dto.PartidoDTO;
import com.mauro.tennis.springtennis.model.Partido;

/**
 * <p>Componente de Mapper</p>
 * Este componente sirve para transformar un objeto de tipo entidad a un objeto de tipo DTO,
 * los atributos deben coincidir en nombre, pero no es necesario que se contenga en el DTO todos
 * los atributos de la entidad, los atributos que no contengan match (mismo nombre) o no existan
 * se instanciaran como null por defecto
 */
public interface PartidoMapper {

    /* Interfaz en donde definimos los metodos que seran
     * obligatorios utilizar en nuestras clases. En este caso
     * PartidoMapperImpl, en su declaracion incluimos:
     * "implements PartidoMapper" */
    PartidoDTO toDTO(Partido entity);
    Partido fromDTO(PartidoDTO entity);

}
