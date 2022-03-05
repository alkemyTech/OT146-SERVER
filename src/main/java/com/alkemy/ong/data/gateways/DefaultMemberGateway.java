package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entity.MemberEntity;
import com.alkemy.ong.data.repository.MemberRepository;
import com.alkemy.ong.domain.members.Member;
import com.alkemy.ong.domain.members.MemberGateway;
import com.alkemy.ong.web.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

@Component
public class DefaultMemberGateway implements MemberGateway {

    private final MemberRepository memberRepository;

    public DefaultMemberGateway(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member create(Member member) {
        return toModel(memberRepository.save(toEntity(member)));
    }

    @Override
    public List<Member> findAll() {
        List<MemberEntity> members = memberRepository.findAll();
        return toModelList(members);
    }

    @Override
    public Member update(long id, Member member) {
        Optional<MemberEntity> entity = memberRepository.findById(id);
        entity.orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        MemberEntity entityUpdate = updateEntity(entity.get(), member);
        return toModel(memberRepository.save(entityUpdate));
    }

    @Override
    public void delete(long id) {
        MemberEntity entity = memberRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        entity.setUpdatedAt(LocalDate.now());
        memberRepository.save(entity);
        memberRepository.deleteById(id);
    }

    @Override
    public List<Member> listByPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return memberRepository.findByDeleted(false, pageable)
                .stream()
                .map(this::toModel)
                .collect(toList());
    }

    private MemberEntity updateEntity(MemberEntity entity, Member member) {
        entity.setName(member.getName());
        entity.setFacebookUrl(member.getFacebookUrl());
        entity.setInstagramUrl(member.getInstagramUrl());
        entity.setLinkedinUrl(member.getLinkedinUrl());
        entity.setImage(member.getImage());
        entity.setDescription(member.getDescription());
        entity.setUpdatedAt(LocalDate.now());
        return entity;
    }

    private MemberEntity toEntity(Member member) {
        return MemberEntity.builder()
                .name(member.getName())
                .facebookUrl(member.getFacebookUrl())
                .instagramUrl(member.getInstagramUrl())
                .linkedinUrl(member.getLinkedinUrl())
                .image(member.getImage())
                .description(member.getDescription())
                .createdAt(toLocalDate(member.getCreatedAt()))
                .build();
    }

    private Member toModel(MemberEntity entity) {
        return Member.builder()
                .id(entity.getId())
                .name(entity.getName())
                .facebookUrl(entity.getFacebookUrl())
                .instagramUrl(entity.getInstagramUrl())
                .linkedinUrl(entity.getLinkedinUrl())
                .image(entity.getImage())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt().toString())
                .build();
    }

    private LocalDate toLocalDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.parse(stringDate, formatter);
    }

    private List<Member> toModelList(List<MemberEntity> members) {
        return members.stream().map(this::toModel).collect(toList());
    }
}
