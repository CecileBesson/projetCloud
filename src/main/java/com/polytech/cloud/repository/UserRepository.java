package com.polytech.cloud.repository;

import com.polytech.cloud.entities.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public UserEntity findById(String id);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserEntity u WHERE u.id =:id")
    public void deleteById(String id);

    @Query(countQuery = "SELECT COUNT(*) FROM user where last_name := lastname", nativeQuery = true)
    public List<UserEntity> findByLastName(String lastName, Pageable paging);

    @Query(value = "SELECT u.* FROM user u, position p WHERE u.fk_position = p.id_position ORDER BY " +
            "ABS(p.lat - :lat) + ABS(p.lon - :lon) ASC LIMIT 10", nativeQuery = true)
    public List<UserEntity> findFirst10NearestUsers(Double lat, Double lon);
    public void deleteAllById(String id);

    @Query(value ="SELECT * from user where TIMESTAMPDIFF(YEAR, birth_day, CURDATE()) = :age", nativeQuery = true)
    public List<UserEntity> findByAgeEq(@Param("age") Integer age, Pageable paging);

    @Query(value ="SELECT * from user where TIMESTAMPDIFF(YEAR, birth_day, CURDATE()) > :age", nativeQuery = true)
    public List<UserEntity> findByAgeGt(@Param("age") Integer age, Pageable paging);

}
