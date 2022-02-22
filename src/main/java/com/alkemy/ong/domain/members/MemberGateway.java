package com.alkemy.ong.domain.members;

import java.util.List;

public interface MemberGateway {

    Member create(Member model);
    List<Member> findAll();
}
