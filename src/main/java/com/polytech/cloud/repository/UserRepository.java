package com.polytech.cloud.repository;

import com.polytech.cloud.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findById(int id);

    @Modifying
    @Query(value = "ALTER TABLE `user` AUTO_INCREMENT = 1",
            nativeQuery = true)
    public void resetAutoIncrementSeed();

    void deleteAllById(int id);

}
