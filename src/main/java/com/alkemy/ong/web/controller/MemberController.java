package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.members.Member;
import com.alkemy.ong.domain.members.MemberService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberDTO> save(@Valid @RequestBody MemberDTO dto) throws Exception {
        Member memberSaved = this.memberService.save(this.toModel(dto));
        MemberDTO resultDTO = this.toDto(memberSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultDTO);
    }

    // =================================================================================================
    private Member toModel(MemberDTO dto) {
        Member memberDomain = Member.builder()
                .name(dto.getName())
                .facebookUrl(dto.getFacebookUrl())
                .instagramUrl(dto.getInstagramUrl())
                .linkedinUrl(dto.getLinkedinUrl())
                .image(dto.getImage())
                .description(dto.getDescription())
                .createdAt(dto.getCreatedAt())
                .build();
        return memberDomain;
    }

    private MemberDTO toDto(Member member) {
        MemberDTO memberDTO = MemberDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .facebookUrl(member.getFacebookUrl())
                .instagramUrl(member.getInstagramUrl())
                .linkedinUrl(member.getLinkedinUrl())
                .image(member.getImage())
                .description(member.getDescription())
                .createdAt(member.getCreatedAt())
                .build();
        return memberDTO;
    }

    @Getter
    @Setter
    @Builder
    public static class MemberDTO {

        private Long id;

        @NotNull
        @NotBlank
        @NotEmpty
        private String name;

        private String facebookUrl;
        private String instagramUrl;
        private String linkedinUrl;

        @NotNull
        @NotBlank
        @NotEmpty
        private String image;

        private String description;
        private String createdAt;
    }
}