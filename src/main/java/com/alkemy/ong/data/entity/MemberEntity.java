package com.alkemy.ong.data.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

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
        final MemberEntity other = (MemberEntity) obj;
        if (other.getId() != null) {
            return other.getName().equals(getName())
                    && other.getId().equals(getId())
                    && other.getFacebookUrl().equals(getFacebookUrl())
                    && other.getInstagramUrl().equals(getInstagramUrl())
                    && other.getLinkedinUrl().equals(getLinkedinUrl())
                    && other.getImage().equals(getImage())
                    && other.getDescription().equals(getDescription())
                    && other.getCreatedAt().equals(getCreatedAt());
        } else {
            return other.getName().equals(getName())
                    && other.getFacebookUrl().equals(getFacebookUrl())
                    && other.getInstagramUrl().equals(getInstagramUrl())
                    && other.getLinkedinUrl().equals(getLinkedinUrl())
                    && other.getImage().equals(getImage())
                    && other.getDescription().equals(getDescription())
                    && other.getCreatedAt().equals(getCreatedAt());
        }
    }

}
