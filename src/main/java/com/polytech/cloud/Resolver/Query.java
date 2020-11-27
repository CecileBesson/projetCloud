package com.polytech.cloud.Resolver;


import com.polytech.cloud.entities.PositionEntity;
import com.polytech.cloud.entities.UserEntity;
import com.polytech.cloud.repository.PositionGraphQLRepository;
import com.polytech.cloud.repository.UserGraphQLRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;

public class Query implements GraphQLQueryResolver {
    private UserGraphQLRepository userGraphQLRepository;
    private PositionGraphQLRepository positionGraphQLRepository;

    public Query(UserGraphQLRepository userGraphQLRepository, PositionGraphQLRepository positionGraphQLRepository) {
        this.userGraphQLRepository = userGraphQLRepository;
        this.positionGraphQLRepository = positionGraphQLRepository;
    }

    public Iterable<UserEntity> findAllUsers() {
        return userGraphQLRepository.findAll();
    }

    public Iterable<PositionEntity> findAllPositions() {
        return positionGraphQLRepository.findAll();
    }

    public long countUsers() {
        return userGraphQLRepository.count();
    }

}
