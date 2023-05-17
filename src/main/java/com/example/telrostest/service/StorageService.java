package com.example.telrostest.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String uploadAvatar(MultipartFile file);

    Resource loadAsResource(String filename);

    String deleteFile(String filename);
}
