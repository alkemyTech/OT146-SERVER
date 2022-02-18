package com.alkemy.ong.domain.service;

import com.alkemy.ong.data.entity.MemberEntity;
import com.alkemy.ong.domain.Member;

public interface MemberGateway {

    MemberEntity memberDomain2MemberEntity(Member member);
    Member memberEntity2MemberDomain(MemberEntity entity);
}
