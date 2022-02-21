package com.alkemy.ong.domain.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commentary {

    private Long userId;
    private String body;
//  private Long newsId;

}
