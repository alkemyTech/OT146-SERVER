package com.alkemy.ong.domain.service.impl;

import com.alkemy.ong.domain.gateway.MemberGateway;
import com.alkemy.ong.domain.model.Member;
import com.alkemy.ong.domain.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private MemberGateway memberGateway;

    @Autowired
    public MemberServiceImpl(MemberGateway memberGateway) {
        this.memberGateway = memberGateway;
    }

    @Override
    public Member save(Member member) {
        return this.memberGateway.create(member);
    }
}
