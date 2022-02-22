package com.alkemy.ong.domain.members;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberGateway memberGateway;

    public MemberServiceImpl(MemberGateway memberGateway) {
        this.memberGateway = memberGateway;
    }

    @Override
    public Member save(Member member) {
        return this.memberGateway.create(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return this.memberGateway.findAll();
    }

}
