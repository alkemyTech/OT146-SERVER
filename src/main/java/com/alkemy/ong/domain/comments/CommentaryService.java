package com.alkemy.ong.domain.comments;

import org.springframework.stereotype.Service;

@Service
public class CommentaryService implements CommentaryGateway {

    private final CommentaryGateway gateway;

    public CommentaryService(CommentaryGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Commentary create(Commentary commentary) {
        return gateway.create(commentary);
    }
}
