package com.alkemy.ong.domain.comments;


import java.util.List;

public interface CommentaryGateway {

    Commentary create(Commentary commentary);

    List<Commentary> findAll();

}
