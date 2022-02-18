package com.alkemy.ong.domain.service;

import com.alkemy.ong.data.entity.Commentary;
import com.alkemy.ong.data.repository.CommentaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultCommentaryGateway implements CommentaryGateway {

    @Autowired
    private CommentaryRepository commentaryRepository;


    @Override
    public Commentary addNewComment(/*CommentaryDTO dto*/) {

        if (validateCommentary(/*dto*/)) {
            Commentary commentary = new Commentary();
        /*
        TODO: convert commentaryDTO to commentary entity
         ---> BeanUtils.copyProperties(dto, commentary);
        */
            return commentaryRepository.save(commentary);
        } else {
            return null;
        }
    }

    private boolean validateCommentary(/*CommentaryDTO dto*/) {
    /*
        if (newsRepository.existsById(dto.getNewsId())
                && userRepository.existsById(dto.getUserId())
                && (!dto.getBody().isEmpty())) {
            return true;
        }
     */
        return false;
    }
}
