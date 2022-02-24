package com.alkemy.ong.domain.members;

import java.util.List;

public interface MemberGateway {

    Member create(Member member);
    List<Member> findAll();
    Member update(long id, Member member);
}
