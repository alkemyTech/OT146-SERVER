package com.alkemy.ong.domain.storage;

import org.springframework.stereotype.Service;

@Service
public class StorageService {
    private final StorageGateway storageGateway;

    public StorageService(StorageGateway storageGateway) { this.storageGateway = storageGateway; }

    // TODO: updateFile / deleteFile ...
}
