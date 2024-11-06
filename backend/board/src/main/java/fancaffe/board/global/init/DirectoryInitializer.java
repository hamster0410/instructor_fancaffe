package fancaffe.board.global.init;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
public class DirectoryInitializer {

    @Value("${file.upload-dir-comment}")  // 댓글 파일 업로드 경로
    private String commentUploadDir;

    @Value("${file.upload-dir-post}")  // 게시글 파일 업로드 경로
    private String postUploadDir;

    @PostConstruct
    public void init() {
        createDirectoryIfNotExists(commentUploadDir);
        createDirectoryIfNotExists(postUploadDir);
    }

    private void createDirectoryIfNotExists(String dirPath) {
        Path path = Paths.get(dirPath);

        if (!Files.exists(path)) {  // 디렉토리 존재 여부 확인
            try {
                Files.createDirectories(path);  // 디렉토리 생성
                log.info("Directory created: " + dirPath);
            } catch (IOException e) {
                log.info("Failed to create directory: " + dirPath);
                e.printStackTrace();
            }
        } else {
            log.info("Directory already exists: " + dirPath);
        }
    }
}

