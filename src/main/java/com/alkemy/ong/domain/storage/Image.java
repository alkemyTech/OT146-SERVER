package com.alkemy.ong.domain.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Image {
    @Getter @Setter
    private String fullName;
    @Getter @Setter
    private String url;

    public Image(String url) {
        this.url = url;
        this.fullName = url.substring(url.lastIndexOf("/")+1);
    }
}
