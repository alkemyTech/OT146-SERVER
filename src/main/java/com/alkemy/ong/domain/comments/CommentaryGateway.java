package com.alkemy.ong.domain.comments;


import com.alkemy.ong.domain.organization.Organization;

import java.util.List;

public interface CommentaryGateway {

    Commentary create(Commentary commentary);

    List<Commentary> findAll();

    Commentary findById(Long id);

    Commentary update(Commentary commentary);

}
