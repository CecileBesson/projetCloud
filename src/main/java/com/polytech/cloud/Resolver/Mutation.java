package com.polytech.cloud.Resolver;


import com.polytech.cloud.repository.PositionGraphQLRepository;
import com.polytech.cloud.repository.UserGraphQLRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;

public class Mutation implements GraphQLMutationResolver {
    private PositionGraphQLRepository positionGraphQLRepository;
    private UserGraphQLRepository userGraphQLRepository;

    public Mutation(UserGraphQLRepository userGraphQLRepository, PositionGraphQLRepository positionGraphQLRepository) {
        this.positionGraphQLRepository = positionGraphQLRepository;
        this.userGraphQLRepository = userGraphQLRepository;
    }


}