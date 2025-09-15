package com.project.zipmin.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private final Path root;
    private static final Set<String> ALLOWED = Set.of("image/png", "image/jpeg", "image/gif");

    public FileService(@Value("${app.upload.dir}") String uploadDir) throws IOException {
        this.root = Paths.get(uploadDir);
        Files.createDirectories(this.root);
    }

    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("빈 파일입니다.");
        }
        if (!ALLOWED.contains(file.getContentType())) {
            throw new IllegalArgumentException("허용되지 않은 MIME 타입: " + file.getContentType());
        }

        String ext = Optional.ofNullable(file.getOriginalFilename())
                .filter(fn -> fn.contains("."))
                .map(fn -> fn.substring(fn.lastIndexOf('.')))
                .orElse("");

        String newName = UUID.randomUUID().toString().replace("-", "") + ext.toLowerCase();
        Path target = root.resolve(newName).normalize();

        // 동일 경로 역참조 차단
        if (!target.getParent().equals(root)) {
            throw new SecurityException("잘못된 경로");
        }

        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return newName; // DB에는 파일명(또는 전체 경로) 저장
    }
}
