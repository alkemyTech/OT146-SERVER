package com.alkemy.ong.data.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

import static com.google.common.base.Objects.equal;

@Entity
@Table(name = "members")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE members SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;

    @Column(nullable = false)
    private String image;
    private String description;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedAt;

    private boolean deleted = Boolean.FALSE;

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MemberEntity entity = (MemberEntity) obj;
        return equal(getId(), entity.getId())
                && equal(getName(), entity.getName())
                && equal(getFacebookUrl(), entity.getFacebookUrl())
                && equal(getInstagramUrl(), entity.getInstagramUrl())
                && equal(getLinkedinUrl(), entity.getLinkedinUrl())
                && equal(getImage(), entity.getImage())
                && equal(getDescription(), entity.getDescription())
                && equal(getCreatedAt(), entity.getCreatedAt());
    }
}
