package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.Member;
import com.alkemy.ong.domain.service.MemberService;
import com.alkemy.ong.web.exception.FieldException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/members")
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberDTO> save(@RequestBody MemberDTO dto) throws Exception {
        if (dto.getName().isEmpty() || dto.getName() == null) {
            throw new FieldException("name field cannot be empty");
        }
        Member memberSaved = this.memberService.save(this.memberDTO2MemberDomain(dto));
        MemberDTO resultDTO = this.memberDomain2MemberDTO(memberSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultDTO);
    }

    // =================================================================================================
    public Member memberDTO2MemberDomain(MemberDTO dto) {
        Member memberDomain = new Member();
        memberDomain.setName(dto.getName());
        memberDomain.setFacebookUrl(dto.getFacebookUrl());
        memberDomain.setInstagramUrl(dto.getInstagramUrl());
        memberDomain.setLinkedinUrl(dto.getLinkedinUrl());
        memberDomain.setImage(dto.getImage());
        memberDomain.setDescription(dto.getDescription());
        memberDomain.setCreatedAt(dto.getCreatedAt());
        return memberDomain;
    }

    public MemberDTO memberDomain2MemberDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setName(member.getName());
        memberDTO.setFacebookUrl(member.getFacebookUrl());
        memberDTO.setInstagramUrl(member.getInstagramUrl());
        memberDTO.setLinkedinUrl(member.getLinkedinUrl());
        memberDTO.setImage(member.getImage());
        memberDTO.setDescription(member.getDescription());
        memberDTO.setCreatedAt(member.getCreatedAt());
        return memberDTO;
    }

    // ======================================================================================================
    @Getter
    @Setter
    public static class MemberDTO {
        private Long id;
        private String name;
        private String facebookUrl;
        private String instagramUrl;
        private String linkedinUrl;
        private String image;
        private String description;
        private String createdAt;
    }
}