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
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (obj instanceof MemberEntity) {
            if (((MemberEntity) obj).getName().equals(getName())) {
                return true;
            }
        }
       return false;
    }
}
