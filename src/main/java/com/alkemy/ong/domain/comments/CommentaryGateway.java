package com.alkemy.ong.domain.comments;


import java.util.List;

public interface CommentaryGateway {

    Commentary create(Commentary commentary);

    List<Commentary> findAll();

    Commentary findById(Long id);

    Commentary update(Commentary commentary);

    void delete(Long id);

    boolean existsById(Long id);

}
