package com.polytech.cloud.repository;

import com.polytech.cloud.entities.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<PositionEntity, Long> {
}
