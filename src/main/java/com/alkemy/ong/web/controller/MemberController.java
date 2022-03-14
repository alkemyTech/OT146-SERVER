package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.members.Member;
import com.alkemy.ong.domain.members.MemberService;
import com.alkemy.ong.web.exceptions.BadRequestException;
import com.alkemy.ong.web.utils.PageResponse;
import com.alkemy.ong.web.utils.PageUtils;
import io.swagger.annotations.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.*;

@RestController
@RequestMapping("/members")
@Api(value = "members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @ApiOperation(value = "Insert the information of a new member to the DB.")
    @PostMapping
    public ResponseEntity<MemberDTO> save(@Valid @RequestBody MemberDTO dto) {
        Member memberSaved = memberService.save(toModel(dto));
        MemberDTO resultDTO = toDto(memberSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Bring all the members of the DB without paging")
    @GetMapping("/all")
    public ResponseEntity<List<MemberDTO>> getMembers() {
        List<Member> memberList = memberService.getAllMembers();
        List<MemberDTO> dtoList = toDtoList(memberList);
        return ResponseEntity.ok().body(dtoList);
    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @ApiOperation(value = "Bring all the members of the DB with pagination")
    @GetMapping(params = {"page"})
    public ResponseEntity<PageResponse<MemberDTO>> getMembersByPage(@RequestParam(name = "page") Integer page) {
        if (page < 0) {
            throw new BadRequestException("Page index not found");
        }

        List<MemberDTO> memberDTOS = memberService.getMembersByPage(page, PageUtils.PAGE_SIZE)
                .stream()
                .map(this::toDto)
                .collect(toList());

        PageResponse<MemberDTO> pageResponse = new PageResponse<>(memberDTOS, "/members", page, PageUtils.PAGE_SIZE);
        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @ApiOperation(value = "Modify a member by id")
    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> update(@Valid @PathVariable long id, @Valid @RequestBody MemberDTO dto) {
        Member member = memberService.update(id, toModel(dto));
        MemberDTO resultDTO = toDto(member);
        return ResponseEntity.ok().body(resultDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete a member by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable long id) {
        memberService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

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

    private List<MemberDTO> toDtoList(List<Member> members) {
        return members.stream().map(this::toDto).collect(toList());
    }

    @Getter
    @Setter
    @Builder
    @ApiModel(description = "member entity representation")
    public static class MemberDTO {

        @ApiModelProperty(dataType = "Long", value = "Member id")
        private Long id;

        @NotNull
        @NotBlank
        @NotEmpty
        @ApiModelProperty(dataType = "String", value = "Member name.", example = "Felipe Herrera", required = true)
        private String name;

        @ApiModelProperty(dataType = "String", value = "Facebook url.", example = "https://www.facebook.com/profile")
        private String facebookUrl;

        @ApiModelProperty(dataType = "String", value = "Instagram url.", example = "https://www.instagram.com/profile")
        private String instagramUrl;

        @ApiModelProperty(dataType = "String", value = "Linkedin url.", example = "https://www.linkedin.com/profile")
        private String linkedinUrl;

        @NotNull
        @NotBlank
        @NotEmpty
        @ApiModelProperty(dataType = "String", value = "Image url.", example = "user/img/photo.jpg", required = true)
        private String image;

        @ApiModelProperty(dataType = "String", value = "Member description.", example = "I am a backend developer")
        private String description;

        @ApiModelProperty(dataType = "String", value = "Creation date.", example = "2022-03-05")
        private LocalDate createdAt;
    }
}