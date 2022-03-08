package com.alkemy.ong.domain.comments;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentaryService {

    private final CommentaryGateway gateway;

    public CommentaryService(CommentaryGateway gateway) {
        this.gateway = gateway;
    }

    public Commentary create(Commentary commentary) {
        return gateway.create(commentary);
    }

    public List<Commentary> findAll() {
        return gateway.findAll();
    }

    public Commentary findById(Long id) {
        return gateway.findById(id);
    }

    public Commentary update(Commentary commentary) {
        return gateway.update(commentary);
    }

    public void delete(Long id) {
        gateway.delete(id);
    }

    public boolean existsById(Long id) {
        return gateway.existsById(id);
    }

    public List<Commentary> findByNewsId(Long newsId) {
        return gateway.findByNewsId(newsId);
    }
}
