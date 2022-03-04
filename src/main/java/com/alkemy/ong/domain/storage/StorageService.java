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
}
