package com.alkemy.ong.domain.comments;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentaryService implements CommentaryGateway {

    private final CommentaryGateway gateway;

    public CommentaryService(CommentaryGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void create(Commentary commentary) {
        gateway.create(commentary);
    }

    @Override
    public List<Commentary> findAll() {
        return gateway.findAll();
    }
}
