package com.polytech.cloud.repository;

import com.polytech.cloud.entities.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PositionRepository extends JpaRepository<PositionEntity, Long> {


    @Modifying
    @Query(value = "ALTER TABLE position AUTO_INCREMENT = 1",
            nativeQuery = true)
    public void resetAutoIncrementSeed();
}
