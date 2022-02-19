package com.alkemy.ong.data.gateway;

import com.alkemy.ong.data.entity.CommentaryEntity;
import com.alkemy.ong.data.entity.UserEntity;
import com.alkemy.ong.data.repository.CommentaryRepository;
import com.alkemy.ong.data.repository.UserRepository;
import com.alkemy.ong.domain.comments.Commentary;
import com.alkemy.ong.domain.comments.CommentaryGateway;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultCommentaryGateway implements CommentaryGateway {

    private final CommentaryRepository commentaryRepository;
    private final UserRepository userRepository;
    //private final NewsRepository newsRepository;

    public DefaultCommentaryGateway(CommentaryRepository commentaryRepository, UserRepository userRepository/*, NewsRepository newsRepository*/) {
        this.commentaryRepository = commentaryRepository;
        this.userRepository = userRepository;
        //this.newsRepository = newsRepository;
    }


    @Override
    public Commentary create(Commentary commentary) { /*
        CommentaryEntity entity;
        Optional<UserEntity> user = userRepository.findById(commentary.getUserId().getId());
        Optional<NewsEntity> news = newsRepository.findById(commentary.getNewsId().getId());
        if (user.isPresent() && news.isPresent()){
            entity = new CommentaryEntity();
            entity.setUserId(user.get());
            entity.setBody(commentary.getBody());
            entity.setNewsId(news.get());
            commentaryRepository.save(entity);
            return commentary;
        } */
        return null;
    }

}
