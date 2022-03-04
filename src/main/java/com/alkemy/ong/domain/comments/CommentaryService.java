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
    public Commentary create(Commentary commentary) {
        return gateway.create(commentary);
    }

    @Override
    public List<Commentary> findAll() {
        return gateway.findAll();
    }

    @Override
    public Commentary findById(Long id) {
        return gateway.findById(id);
    }

    @Override
    public Commentary update(Commentary commentary) {
        return gateway.update(commentary);
    }
}
