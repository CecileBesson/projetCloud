package com.polytech.cloud.repository;

import com.polytech.cloud.entities.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public UserEntity findById(String id);

    public void deleteById(String id);

    public void deleteAllById(String id);

    @Query(value ="SELECT * from user where TIMESTAMPDIFF(YEAR, birth_day, CURDATE()) = :age",
            countQuery = "SELECT count(*) FROM user where TIMESTAMPDIFF(YEAR, birth_day, CURDATE()) = :age", nativeQuery = true)
    public Page<UserEntity> findByAgeEq(@Param("age") Integer age, Pageable paging);

    @Query(value ="SELECT * from user where TIMESTAMPDIFF(YEAR, birth_day, CURDATE()) > :age",
            countQuery = "SELECT count(*) from user where TIMESTAMPDIFF(YEAR, birth_day, CURDATE()) > :age", nativeQuery = true)
    public Page<UserEntity> findByAgeGt(@Param("age") Integer age, Pageable paging);

}
