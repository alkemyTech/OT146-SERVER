package com.alkemy.ong.domain.members;

import java.util.List;

public interface MemberService {
    Member save(Member member);
    List<Member> getAllMembers();
    Member update(long id, Member member);
    void delete(long id);
}
