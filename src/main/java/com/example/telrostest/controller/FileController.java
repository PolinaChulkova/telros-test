package com.example.telrostest.controller;

import com.example.telrostest.service.StorageService;
import com.example.telrostest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;


@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final StorageService storageService;
    private final UserService userService;

    /**
     * Загрузка аватара текущему пользователю
     *
     * @param file      - файл картинки
     * @param principal - интерфейс, получаемый из запроса и предоставляющий логин текущего пользователя
     * @return строка с подтверждением успешной загрузки
     */
    @PostMapping("/avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("avatar") MultipartFile file, Principal principal) {
        String filename = storageService.uploadAvatar(file);
        userService.loadAvatar(principal.getName(), filename);
        return ResponseEntity.ok().body(principal.getName() + ", ваш аватар загружен.");
    }

    /**
     * Скачивание файла аватара
     *
     * @param filename - имя файла
     * @return файл .jpeg
     */
    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFileByFilename(@PathVariable String filename) {
        Resource resource = storageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    /**
     * Удаление файла
     *
     * @param filename - имя файла
     * @param login    - логин пользователя, у которого нужно удалить аватар
     * @return строка с подтверждением удаления
     */
    @DeleteMapping("/{filename:.+}/{login}")
    public ResponseEntity<String> deleteAvatarByFilenameAndLogin(@PathVariable String filename,
                                                                 @PathVariable String login) {
        storageService.deleteFile(filename);
        userService.loadAvatar(login, null);
        return ResponseEntity.ok("Файл " + filename + " удален.");
    }
}
