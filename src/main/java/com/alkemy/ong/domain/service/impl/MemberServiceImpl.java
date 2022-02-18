package com.alkemy.ong.domain.service.impl;

import com.alkemy.ong.data.DefaultMemberGateway;
import com.alkemy.ong.data.entity.MemberEntity;
import com.alkemy.ong.data.repository.MemberRepository;
import com.alkemy.ong.domain.Member;
import com.alkemy.ong.domain.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;
    private DefaultMemberGateway memberGateway;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, DefaultMemberGateway memberGateway) {
        this.memberRepository = memberRepository;
        this.memberGateway = memberGateway;
    }

    @Override
    public Member save(Member member) {
        MemberEntity entity = this.memberGateway.memberDomain2MemberEntity(member);
        MemberEntity entitySaved = this.memberRepository.save(entity);
        Member resultDomain = this.memberGateway.memberEntity2MemberDomain(entitySaved);
        return resultDomain;
    }
}
