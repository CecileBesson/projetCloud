package com.polytech.cloud.repository;

import com.polytech.cloud.entities.PositionEntity;
import org.springframework.data.repository.CrudRepository;

public interface PositionGraphQLRepository extends CrudRepository<PositionEntity, Long> {
}
