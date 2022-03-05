package com.alkemy.ong.domain.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageGateway {

    public Image save(MultipartFile multipartFile);

}
