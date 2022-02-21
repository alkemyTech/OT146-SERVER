package com.alkemy.ong.data.gateway;

import com.alkemy.ong.data.entity.CommentaryEntity;
import com.alkemy.ong.data.entity.UserEntity;
import com.alkemy.ong.data.repository.CommentaryRepository;
import com.alkemy.ong.data.repository.UserRepository;
import com.alkemy.ong.domain.comments.Commentary;
import com.alkemy.ong.domain.comments.CommentaryGateway;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

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
    public void create(Commentary commentary) {
        UserEntity user = userRepository.findById(commentary.getUserId())
                .orElseThrow(
                        () -> new EntityNotFoundException("User with id: " + commentary.getUserId() + " not found.")
                );
//        NewsEntity news = newsRepository.findById(commentary.getNewsId())
//                .orElseThrow(
//                        () -> new EntityNotFoundException("News with id: " + commentary.getNewsId() + " not found.")
//                );
        CommentaryEntity entity = new CommentaryEntity();
        entity.setUserId(user);
        entity.setBody(commentary.getBody());
//      entity.setNewsId(news.get());
        commentaryRepository.save(entity);

    }

    @Override
    public List<Commentary> findAll() {
        List<CommentaryEntity> entities = commentaryRepository.findAll();
        return entities.stream()
                .map(entity -> new Commentary(entity.getUserId().getId(), entity.getBody()))
                .collect(Collectors.toList());
    }

}
