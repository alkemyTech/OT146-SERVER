package com.alkemy.ong.domain.comments;


import java.util.List;

public interface CommentaryGateway {

    void create(Commentary commentary);

    List<Commentary> findAll();

}
