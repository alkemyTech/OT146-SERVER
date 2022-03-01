package com.alkemy.ong.domain.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@AllArgsConstructor
public class Image {
    @Getter @Setter
    private String fullName;
    @Getter @Setter
    private String url;
}
