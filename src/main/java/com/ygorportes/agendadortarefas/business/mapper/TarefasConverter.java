package com.ygorportes.agendadortarefas.business.mapper;

import com.ygorportes.agendadortarefas.business.dto.TarefasDTO;
import com.ygorportes.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    TarefasEntity paraTarefaEntity(TarefasDTO tarefasDTO);

    TarefasDTO paraTarefaDTO(TarefasEntity tarefasEntity);

    List<TarefasEntity> paraListaTarefasEntity(List<TarefasDTO> tarefasDTOList);

    List<TarefasDTO> paraListaTarefasDTO(List<TarefasEntity> tarefasEntityList);
}
