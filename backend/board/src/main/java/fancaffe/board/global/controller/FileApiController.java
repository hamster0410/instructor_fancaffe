package fancaffe.board.global.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/file")
public class FileApiController {
    @Value("${file.upload-dir-comment}")  // 파일 저장 경로를 application.properties에 설정
    private String commentUploadDir;

    @Value("${file.upload-dir-post}")  // 파일 저장 경로를 application.properties에 설정
    private String postUploadDir;

    //    @GetMapping("/post")
//    public byte[] printImagePost(){
//
//    }
    @GetMapping(value = "/comment", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public ResponseEntity<byte[]> printImagePost(@RequestParam(value="filename") final String filename) {
        // 업로드된 파일의 전체 경로
        String fileFullPath = Paths.get(commentUploadDir, filename).toString();
        System.out.println(fileFullPath);

        // 파일 객체 생성
        File uploadedFile = new File(fileFullPath);

        // 파일 존재 여부 확인
        if (!uploadedFile.exists()) {
            System.out.println("file not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("File not found".getBytes());
        }

        try {
            // 이미지 파일을 byte[]로 변환 후 반환
            byte[] imageBytes = Files.readAllBytes(uploadedFile.toPath());

            // 파일의 MediaType 설정
            MediaType mediaType = getMediaTypeForFileName(filename);

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(imageBytes);

        } catch (IOException e) {
            // 예외 발생 시 Internal Server Error와 함께 메시지 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error reading file".getBytes());
        }
    }

    private MediaType getMediaTypeForFileName(String filename) {
        if (filename.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".jfif")) {
            return MediaType.IMAGE_JPEG;
        } else if (filename.endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        } else if (filename.endsWith(".avif")) {
            return MediaType.parseMediaType("image/avif");
        }else {
            return MediaType.APPLICATION_OCTET_STREAM; // 기본값
        }
    }


}
