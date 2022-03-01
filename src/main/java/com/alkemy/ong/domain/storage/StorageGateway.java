package com.alkemy.ong.domain.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageGateway {

    public Image save(MultipartFile multipartFile);

    public Image update(Image image, MultipartFile multipartFile);

    public void delete(Image image);

}
