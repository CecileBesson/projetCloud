package com.polytech.cloud.repository;

import com.polytech.cloud.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public UserEntity findById(String id);

    public void deleteById(String id);

    public void deleteAllById(String id);
}
