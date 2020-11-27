package com.polytech.cloud.Resolver;

import com.polytech.cloud.repository.UserRepository;
import graphql.kickstart.tools.GraphQLResolver;


public class PositionResolver implements GraphQLResolver {
    private UserRepository userRepository;

    public PositionResolver(UserRepository userRepository){
        this.userRepository = userRepository;
    }


}
