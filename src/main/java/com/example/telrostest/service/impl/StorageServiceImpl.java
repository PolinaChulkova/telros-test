package com.example.telrostest.service.impl;

import com.example.telrostest.exception.FileStorageException;
import com.example.telrostest.service.StorageService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class StorageServiceImpl implements StorageService {

    @Value("${storage.location}")
    private Path rootLocation;

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(rootLocation);
    }

    /**
     * Загрузка аватара пользователя
     *
     * @return имя загруженного файла
     */
    @Override
    @Transactional
    public String uploadAvatar(MultipartFile file) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        if (file.isEmpty())
            throw new FileStorageException(String.format("Не удалось сохранить пустой файл %s!", filename));

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new FileStorageException(String.format("Не удалось сохранить файл %s! Error: [%s]", filename, e));
        }

        return filename;
    }

    /**
     * Скачивание файла
     */
    @Override
    public Resource loadAsResource(String filename) {
        try {
            File file = new File(rootLocation + File.separator + filename);
            Path path = Paths.get(file.getAbsolutePath());
            return new ByteArrayResource(Files.readAllBytes(path));

        } catch (IOException e) {
            throw new FileStorageException(String.format("Не удалось загрузить файл %s! Error: [%s].", filename, e));
        }
    }

    @Override
    public String deleteFile(String filename) {
        try {
            Files.deleteIfExists(new File(rootLocation + File.separator + filename).toPath());

        } catch (IOException e) {
            throw new FileStorageException(String.format("Не удалось удалить файл \"%s\".", filename));
        }

        return filename;
    }
}

