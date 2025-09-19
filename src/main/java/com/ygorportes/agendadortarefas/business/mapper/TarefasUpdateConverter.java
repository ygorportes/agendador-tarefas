package com.ygorportes.agendadortarefas.business.mapper;

import com.ygorportes.agendadortarefas.business.dto.TarefasDTO;
import com.ygorportes.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TarefasUpdateConverter {

    void updateDeTarefas(TarefasDTO tarefasDTO, @MappingTarget TarefasEntity tarefasEntity);
}
