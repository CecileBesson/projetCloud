package com.polytech.cloud.repository;

import com.polytech.cloud.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserGraphQLRepository extends CrudRepository<UserEntity, Long> {
}
