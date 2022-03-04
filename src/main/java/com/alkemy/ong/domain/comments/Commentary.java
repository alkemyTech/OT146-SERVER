package com.alkemy.ong.domain.comments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commentary {

    private Long id;
    private Long userId;
    private String body;
    private Long newsId;

}
