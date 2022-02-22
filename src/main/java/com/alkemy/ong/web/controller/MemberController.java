package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.members.Member;
import com.alkemy.ong.domain.members.MemberService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberDTO> save(@Valid @RequestBody MemberDTO dto) {
        Member memberSaved = this.memberService.save(this.toModel(dto));
        MemberDTO resultDTO = this.toDto(memberSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultDTO);
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getMembers() {
        List<Member> memberList = this.memberService.getAllMembers();
        List<MemberDTO> dtoList = this.toDtoList(memberList);
        return ResponseEntity.ok().body(dtoList);
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

    private List<MemberDTO> toDtoList(List<Member> memberList) {
        List<MemberDTO> dtoList = new ArrayList<>();
        for (Member member : memberList) {
            dtoList.add(this.toDto(member));
        }
        return dtoList;
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