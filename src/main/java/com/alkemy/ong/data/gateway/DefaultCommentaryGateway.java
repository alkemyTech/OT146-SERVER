package com.alkemy.ong.data.gateway;

import com.alkemy.ong.data.entity.CommentaryEntity;
import com.alkemy.ong.data.entity.UserEntity;
import com.alkemy.ong.data.repository.CommentaryRepository;
import com.alkemy.ong.data.repository.UserRepository;
import com.alkemy.ong.domain.comments.Commentary;
import com.alkemy.ong.domain.comments.CommentaryGateway;
import org.springframework.stereotype.Component;

@Component
public class DefaultCommentaryGateway implements CommentaryGateway {

    private final CommentaryRepository commentaryRepository;
    private final UserRepository userRepository;
    //private final NewsRepository newsRepository;

    public DefaultCommentaryGateway(CommentaryRepository commentaryRepository, UserRepository userRepository /*, NewsRepository newsRepository*/) {
        this.commentaryRepository = commentaryRepository;
        this.userRepository = userRepository;
        //this.newsRepository = newsRepository;
    }


    @Override
    public Commentary create(Commentary commentary) {
        UserEntity user = new userRepository.findById(commentary.getUserId().getId()).get();
        NewsEntity news = new newsRepository.findById(commentary.getNewsId().getId()).get();

        CommentaryEntity entity = new CommentaryEntity();
        entity.setUserId(user);
        entity.setBody(commentary.getBody());
        entity.setNewsId(news);
        commentaryRepository.save(entity);

        return commentary;
    }

}
