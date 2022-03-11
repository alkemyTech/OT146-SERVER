package com.alkemy.ong.domain.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class StorageService {
    private final StorageGateway storageGateway;

    public StorageService(StorageGateway storageGateway) { this.storageGateway = storageGateway; }

    public Image save(MultipartFile multipartFile){
        return this.storageGateway.save(multipartFile);
    }

    public Image save(String base64, String fileName){
        return this.storageGateway.save(base64, fileName);
    }

}
