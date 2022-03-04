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
        return memberGateway.create(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberGateway.findAll();
    }

    @Override
    public Member update(long id, Member member) {
        return memberGateway.update(id, member);
    }

    @Override
    public void delete(long id) {
        memberGateway.delete(id);
    }

    @Override
    public List<Member> getMembersByPage(int page, int pageSize) {
        return memberGateway.listByPage(page, pageSize);
    }

}
