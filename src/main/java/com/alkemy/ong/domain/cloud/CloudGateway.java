package com.alkemy.ong.domain.cloud;

import org.springframework.web.multipart.MultipartFile;

public interface CloudGateway {

    public Image save(MultipartFile multipartFile);

    public Image save(String base64, String fileName);

}
