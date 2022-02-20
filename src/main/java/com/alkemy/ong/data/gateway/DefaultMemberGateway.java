package com.alkemy.ong.data.gateway;

import com.alkemy.ong.data.entity.MemberEntity;
import com.alkemy.ong.data.repository.MemberRepository;
import com.alkemy.ong.domain.model.Member;
import com.alkemy.ong.domain.gateway.MemberGateway;
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
        MemberEntity entity = this.memberModel2MemberEntity(model);
        MemberEntity entitySaved = this.memberRepository.save(entity);
        return this.memberEntity2MemberModel(entitySaved);
    }

    //======================================================================================================
    public MemberEntity memberModel2MemberEntity(Member member) {
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

    public Member memberEntity2MemberModel(MemberEntity entity) {
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
