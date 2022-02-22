package com.alkemy.ong.data.gateway;

import com.alkemy.ong.data.entity.CommentaryEntity;
import com.alkemy.ong.data.entity.NewsEntity;
import com.alkemy.ong.data.entity.UserEntity;
import com.alkemy.ong.data.repository.CommentaryRepository;
import com.alkemy.ong.data.repository.NewsRepository;
import com.alkemy.ong.data.repository.UserRepository;
import com.alkemy.ong.domain.comments.Commentary;
import com.alkemy.ong.domain.comments.CommentaryGateway;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultCommentaryGateway implements CommentaryGateway {

    private final CommentaryRepository commentaryRepository;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;

    public DefaultCommentaryGateway(CommentaryRepository commentaryRepository, UserRepository userRepository, NewsRepository newsRepository) {
        this.commentaryRepository = commentaryRepository;
        this.userRepository = userRepository;
        this.newsRepository = newsRepository;
    }


    @Override
    public Commentary create(Commentary commentary) {
        UserEntity user = getUserEntity(commentary.getUserId());
        NewsEntity news = getNewsEntity(commentary.getNewsId());
        CommentaryEntity entity = getCommentaryEntity(commentary, user, news);
        commentaryRepository.save(entity);
        return getCommentary(entity);
    }

    @Override
    public List<Commentary> findAll() {
        List<CommentaryEntity> entities = commentaryRepository.findAll();
        return entities.stream()
                .map(toDomain())
                .collect(toList());
    }


    private UserEntity getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("User with id: " + userId + " not found.")
                );
    }

    private NewsEntity getNewsEntity(Long newsId) {
        return newsRepository.findById(newsId)
                .orElseThrow(
                        () -> new EntityNotFoundException("News with id: " + newsId + " not found.")
                );
    }

    private Function<CommentaryEntity, Commentary> toDomain() {
        return entity -> new Commentary(entity.getUserId().getId(), entity.getBody(), entity.getNewsId().getId());
    }

    private CommentaryEntity getCommentaryEntity(Commentary commentary, UserEntity user, NewsEntity news) {
        return CommentaryEntity.builder()
                .userId(user)
                .body(commentary.getBody())
                .newsId(news)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private Commentary getCommentary(CommentaryEntity entity) {
        return Commentary.builder()
                .userId(entity.getUserId().getId())
                .body(entity.getBody())
                .newsId(entity.getNewsId().getId())
                .build();
    }

}
