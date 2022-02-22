package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entity.MemberEntity;
import com.alkemy.ong.data.repository.MemberRepository;
import com.alkemy.ong.domain.members.Member;
import com.alkemy.ong.domain.members.MemberGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultMemberGateway implements MemberGateway {

    private final MemberRepository memberRepository;

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

    @Override
    public List<Member> findAll() {
        List<MemberEntity> entityList = this.memberRepository.findAll();
        return this.toModelList(entityList);
    }

    //======================================================================================================
    public MemberEntity toEntity(Member member) {
        return MemberEntity.builder()
                .name(member.getName())
                .facebookUrl(member.getFacebookUrl())
                .instagramUrl(member.getInstagramUrl())
                .linkedinUrl(member.getLinkedinUrl())
                .image(member.getImage())
                .description(member.getDescription())
                .createdAt(this.toLocalDate(member.getCreatedAt()))
                .build();
    }

    public Member toModel(MemberEntity entity) {
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

    public List<Member> toModelList(List<MemberEntity> entityList) {
        List<Member> memberList = new ArrayList<>();
        for (MemberEntity entity : entityList) {
            memberList.add(this.toModel(entity));
        }
        return memberList;
    }
}
