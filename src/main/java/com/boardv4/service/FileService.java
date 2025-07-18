package com.boardv4.service;

import com.boardv4.exception.file.FileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * 파일 업로드, 다운로드, 삭제, 미리보기 기능을 제공하는 서비스 클래스입니다.
 */
@Service
@Slf4j
public class FileService {
    private final String BASE_DIR;

    public FileService(@Value("${file.base.dir}") String BASE_DIR) {
        this.BASE_DIR = BASE_DIR;
    }

    /**
     * Multipart 파일을 지정된 하위 디렉토리에 저장합니다.
     * 저장된 파일명은 UUID로 생성됩니다.
     *
     * @param file         업로드할 파일
     * @param subDirectory 저장할 하위 디렉토리 (예: "posts", "temp")
     * @return 저장된 파일명 (예: "uuid.ext")
     * @throws IllegalArgumentException 파일이 비어있거나 확장자가 없는 경우
     * @throws RuntimeException         파일 저장 실패 시
     */
    public String uploadFile(MultipartFile file, String subDirectory) {
        // 1. 파일이 비었거나 null인 경우 예외 발생
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("빈 파일은 업로드할 수 없습니다.");
        }

        // 2. 원본 파일명 확인 및 확장자 포함 여부 검사
        String originalFilename = file.getOriginalFilename();
        if (!originalFilename.contains(".")) {
            throw new IllegalArgumentException("올바른 파일명이 아닙니다.");
        }

        // 3. 파일명을 UUID로 설정하여 충돌 방지
        String fileUUID = UUID.randomUUID().toString();
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = fileUUID + ext; // 저장할 파일명 구성

        // 4. 전체 파일 경로 생성 (기본 디렉토리 + 서브 디렉토리 + 파일명)
        Path filePath = Paths.get(BASE_DIR, subDirectory, fileName);
        try {
            // 5. 상위 디렉토리가 없을 경우 생성
            Files.createDirectories(filePath.getParent());

            // 6. 파일 저장
            file.transferTo(filePath.toFile());

            // 7. 저장된 파일명 반환
            return fileName;
        } catch (IOException e) {
            // 업로드 실패 시 로깅 및 런타임 예외 발생
            log.error("File upload failed: {}", e.getMessage());
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }


    /**
     * 파일을 다운로드할 수 있도록 ResponseEntity<Resource> 형태로 반환합니다.
     * Content-Disposition 헤더를 통해 브라우저에서 다운로드되도록 설정합니다.
     *
     * @param subDirectory     파일이 저장된 하위 디렉토리
     * @param savedFilename    저장된 파일명 (UUID 형식)
     * @param originalFilename 사용자에게 보여줄 원본 파일명
     * @return 파일 다운로드용 ResponseEntity
     * @throws FileNotFoundException 파일이 존재하지 않거나 읽기 실패 시
     */
    public ResponseEntity<Resource> downloadFile(String subDirectory, String savedFilename, String originalFilename) {
        // 1. 저장된 파일의 전체 경로 생성
        Path filePath = Paths.get(BASE_DIR, subDirectory, savedFilename);

        // 2. 파일이 존재하지 않으면 예외 발생
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException(savedFilename);
        }

        try {
            // 3. 파일을 읽어 Resource 객체로 변환 (스트리밍 대응)
            Resource resource = new InputStreamResource(Files.newInputStream(filePath));

            // 4. 사용자에게 보여줄 파일명을 UTF-8로 인코딩 (브라우저 호환성)
            String encodedFileName = URLEncoder.encode(originalFilename, StandardCharsets.UTF_8)
                    .replace("+", "%20"); // 공백 문자 보정
            // 5. 응답 본문 구성 및 헤더 설정
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + encodedFileName +
                                    "\"; filename*=UTF-8''" + encodedFileName)
                    .body(resource);

        } catch (IOException e) {
            throw new RuntimeException("파일 다운로드 실패", e);
        }
    }

    /**
     * 파일을 브라우저에서 바로 미리보기할 수 있도록 반환합니다.
     * Content-Disposition 없이 바이너리 응답으로 처리됩니다.
     *
     * @param subDirectory     파일이 저장된 하위 디렉토리
     * @param savedFilename    저장된 파일명
     * @param originalFilename 사용자에게 보여줄 원본 파일명 (미리보기 시 활용)
     * @return 파일 미리보기용 ResponseEntity
     * @throws RuntimeException 파일이 존재하지 않거나 읽기 실패 시
     */
    public ResponseEntity<Resource> preview(String subDirectory, String savedFilename, String originalFilename) {
        // 1. 저장된 파일의 전체 경로 생성
        Path filePath = Paths.get(BASE_DIR, subDirectory, savedFilename);

        // 2. 파일이 존재하지 않으면 예외 발생
        if (!Files.exists(filePath)) {
            throw new RuntimeException("File not found");
        }

        try {
            // 3. 파일을 읽어 Resource 객체로 변환 (스트리밍 대응)
            Resource resource = new InputStreamResource(Files.newInputStream(filePath));

            // 4. 응답 본문 구성 및 헤더 설정
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (IOException e) {
            throw new RuntimeException("파일 다운로드 실패", e);
        }
    }

    /**
     * 저장된 파일을 삭제합니다.
     *
     * @param subDirectory  파일이 저장된 하위 디렉토리
     * @param savedFilename 저장된 파일명
     */
    public void deleteFile(String subDirectory, String savedFilename) {
        Path filePath = Paths.get(BASE_DIR, subDirectory, savedFilename);
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            log.info("{}", e.getMessage());
        }
    }
}

