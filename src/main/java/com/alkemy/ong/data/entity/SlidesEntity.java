package com.alkemy.ong.data.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "slides")
public class SlidesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @Column
    private String text;

    @Column(nullable = false)
    private Integer slideOrder;

    // TODO: relación cuando esté disponible el OrganizationEntity
//    @ManyToOne
//    @Column(nullable = false)
//    private OrganizationEntity organizationId;

    @Column
    private Boolean deleted = Boolean.FALSE;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
