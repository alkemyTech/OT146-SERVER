package com.alkemy.ong.data;

import com.alkemy.ong.data.entity.MemberEntity;
import com.alkemy.ong.domain.Member;
import com.alkemy.ong.domain.service.MemberGateway;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DefaultMemberGateway implements MemberGateway {

    @Override
    public MemberEntity memberDomain2MemberEntity(Member member) {
        MemberEntity entity = new MemberEntity();
        entity.setName(member.getName());
        entity.setFacebookUrl(member.getFacebookUrl());
        entity.setInstagramUrl(member.getInstagramUrl());
        entity.setLinkedinUrl(member.getLinkedinUrl());
        entity.setImage(member.getImage());
        entity.setDescription(member.getDescription());
        entity.setCreatedAt(this.string2LocalDate(member.getCreatedAt()));
        return entity;
    }

    @Override
    public Member memberEntity2MemberDomain(MemberEntity entity) {
        Member member = new Member();
        member.setId(entity.getId());
        member.setName(entity.getName());
        member.setFacebookUrl(entity.getFacebookUrl());
        member.setInstagramUrl(entity.getInstagramUrl());
        member.setLinkedinUrl(entity.getLinkedinUrl());
        member.setImage(entity.getImage());
        member.setDescription(entity.getDescription());
        member.setCreatedAt(entity.getCreatedAt().toString());
        return member;
    }

    private LocalDate string2LocalDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.parse(stringDate, formatter);
        return date;
    }
}
