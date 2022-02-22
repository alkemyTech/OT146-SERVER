package com.alkemy.ong.domain.members;

import java.util.List;

public interface MemberService {
    Member save(Member memberDTO2MemberDomain);
    List<Member> getAllMembers();
}
