package com.alkemy.ong.data.gateway;

import com.alkemy.ong.data.entity.MemberEntity;
import com.alkemy.ong.data.repository.MemberRepository;
import com.alkemy.ong.domain.members.Member;
import com.alkemy.ong.domain.members.MemberGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DefaultMemberGateway implements MemberGateway {

    private MemberRepository memberRepository;

    @Autowired
    public DefaultMemberGateway(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member create(Member model) {
        MemberEntity entity = this.toEntity(model);
        MemberEntity entitySaved = this.memberRepository.save(entity);
        return this.toModel(entitySaved);
    }

    //======================================================================================================
    public MemberEntity toEntity(Member member) {
        MemberEntity entity = MemberEntity.builder()
                .name(member.getName())
                .facebookUrl(member.getFacebookUrl())
                .instagramUrl(member.getInstagramUrl())
                .linkedinUrl(member.getLinkedinUrl())
                .image(member.getImage())
                .description(member.getDescription())
                .createdAt(this.toLocalDate(member.getCreatedAt()))
                .build();
        return entity;
    }

    public Member toModel(MemberEntity entity) {
        Member member = Member.builder()
                .id(entity.getId())
                .name(entity.getName())
                .facebookUrl(entity.getFacebookUrl())
                .instagramUrl(entity.getInstagramUrl())
                .linkedinUrl(entity.getLinkedinUrl())
                .image(entity.getImage())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt().toString())
                .build();
        return member;
    }

    private LocalDate toLocalDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.parse(stringDate, formatter);
        return date;
    }

}
