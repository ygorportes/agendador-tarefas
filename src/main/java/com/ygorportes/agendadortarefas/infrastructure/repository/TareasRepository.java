package com.ygorportes.agendadortarefas.infrastructure.repository;

import com.ygorportes.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TareasRepository  extends MongoRepository<TarefasEntity, String> {
}
