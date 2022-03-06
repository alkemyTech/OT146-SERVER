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

    @Override
    public void delete(Long id) {
        gateway.delete(id);
    }

    @Override
    public boolean existsById(Long id) {
        return gateway.existsById(id);
    }

    @Override
    public List<Commentary> findByNewsId(Long newsId) {
        return gateway.findByNewsId(newsId);
    }

}
