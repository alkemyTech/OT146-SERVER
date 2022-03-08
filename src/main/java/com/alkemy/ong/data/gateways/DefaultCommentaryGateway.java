package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entity.CommentaryEntity;
import com.alkemy.ong.data.entity.NewsEntity;
import com.alkemy.ong.data.entity.UserEntity;
import com.alkemy.ong.data.repository.CommentaryRepository;
import com.alkemy.ong.data.repository.NewsRepository;
import com.alkemy.ong.data.repository.UserRepository;
import com.alkemy.ong.domain.comments.Commentary;
import com.alkemy.ong.domain.comments.CommentaryGateway;
import com.alkemy.ong.domain.news.News;
import com.alkemy.ong.web.exceptions.BadRequestException;
import com.alkemy.ong.web.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDateTime;
import java.util.List;

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
        CommentaryEntity commentaryEntity = toEntity(commentary, user, news);
        return toModel(commentaryRepository.save(commentaryEntity));
    }

    @Override
    public List<Commentary> findAll() {
        List<CommentaryEntity> entities = entitiesDescOrder();
        return entities.stream()
                .map(this::toModel)
                .collect(toList());
    }

    @Override
    public Commentary findById(Long id) {
        CommentaryEntity comm= commentaryRepository.findById(id).orElseThrow(() -> new BadRequestException("mensaje"));
        return toModel(comm);
    }

    @PutMapping("/{id}")
    public Commentary update(Commentary commentary) {
        CommentaryEntity commEntity = commentaryRepository.findById(commentary.getId()).orElseThrow(() -> new ResourceNotFoundException("message"));

        return toModel(commentaryRepository.save(newUpdate(commEntity, commentary)));
    }

    @Override
    public void delete(Long id) {
        commentaryRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return commentaryRepository.existsById(id);
    }

    @Override
    public List<Commentary> findByNewsId(Long newsId) {
        List<CommentaryEntity> entities = commentaryRepository.findByNewsEntityId(newsId);
        return toModelList(entities);
    }


    private CommentaryEntity newUpdate(CommentaryEntity commentaryEntity, Commentary commentary) {
        commentaryEntity.setBody(commentary.getBody());
        return commentaryEntity;
    }

    private List<CommentaryEntity> entitiesDescOrder() {
        return commentaryRepository.findAll(Sort.by(Sort.Direction.DESC,"createdAt"));
    }

    private UserEntity getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(
                        () -> new BadRequestException("User with id: " + userId + " not found.")
                );
    }

    private NewsEntity getNewsEntity(Long newsId) {
        return newsRepository.findById(newsId)
                .orElseThrow(
                        () -> new BadRequestException("News with id: " + newsId + " not found.")
                );
    }


    private CommentaryEntity toEntity(Commentary commentary, UserEntity user, NewsEntity news) {
        return CommentaryEntity.builder()
                .userId(user)
                .body(commentary.getBody())
                .newsEntity(newsRepository.findById(commentary.getNewsId()).orElseThrow(
                        () -> new BadRequestException("News id not found.")
                ))
                .createdAt(LocalDateTime.now())
                .id(commentary.getUserId())
                .build();
    }

    private Commentary toModel(CommentaryEntity entity) {
        return Commentary.builder()
                .userId(entity.getUserId().getId())
                .body(entity.getBody())
                .newsId(entity.getNewsEntity().getId())
                .id(entity.getId())
                .build();
    }

    private List<Commentary> toModelList(List<CommentaryEntity> comments) {
        return comments.stream().map(this::toModel).collect(toList());
    }
}
