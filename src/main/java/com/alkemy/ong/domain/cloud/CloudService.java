package com.alkemy.ong.domain.cloud;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class CloudService {
    private final CloudGateway cloudGateway;

    public CloudService(CloudGateway cloudGateway) { this.cloudGateway = cloudGateway; }

    public Image save(MultipartFile multipartFile){
        return this.cloudGateway.save(multipartFile);
    }

    public Image save(String base64, String fileName){
        return this.cloudGateway.save(base64, fileName);
    }

}
