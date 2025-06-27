package com.boardv4.service;

import com.boardv4.domain.PostFile;
import com.boardv4.dto.post.PostModifyRequest;
import com.boardv4.exception.postFile.PostFileNotFoundException;
import com.boardv4.repository.PostFileRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 게시글 첨부 파일 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
@AllArgsConstructor
public class PostFileService {
    private final FileService fileService;
    private final PostFileRepository postFileRepository;

    /**
     * 파일 저장이름을 기반으로 첨부 파일을 조회합니다.
     *
     * @param savedFilename 저장 파일 목록
     * @return PostFile 객체
     */
    public PostFile getPostFileBySavedName(String savedFilename) {
        return postFileRepository.findBySavedFilename(savedFilename)
                .orElseThrow(() -> new PostFileNotFoundException(savedFilename));
    }

    /**
     * 게시글 ID를 기반으로 첨부 파일 목록을 조회합니다.
     *
     * @param id 게시글 ID
     * @return 해당 게시글의 첨부 파일 목록
     */
    public List<PostFile> getFilesByPostId(Long id) {
        return postFileRepository.findByPostId(id);
    }

    /**
     * 저장된 파일명을 통해 파일을 다운로드합니다.
     *
     * @param savedFilename 저장된 파일명 (UUID 확장자 형식)
     * @return 다운로드용 ResponseEntity<Resource>
     */
    public ResponseEntity<Resource> download(String savedFilename) {
        PostFile postFile = getPostFileBySavedName(savedFilename);

        return fileService.downloadFile(postFile.getPath(), postFile.getSavedFilename(), postFile.getOriginalFilename());
    }

    /**
     * 다중 파일을 업로드하고, DB에 첨부파일 정보를 저장합니다.
     *
     * @param boardEngName 게시판 영문명 (저장 경로에 사용)
     * @param postId       게시글 ID
     * @param files        Multipart 첨부 파일 목록
     */
    public void uploadMultipleFile(String boardEngName, Long postId, List<MultipartFile> files) {
        List<PostFile> postFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            String savedFilename = fileService.uploadFile(file, boardEngName);

            postFiles.add(PostFile.of(postId, boardEngName, savedFilename, file.getOriginalFilename()));
        }

        if (!postFiles.isEmpty()) {
            postFileRepository.insertFiles(postFiles);
        }
    }

    public void deleteByPostId(Long id) {
        List<PostFile> postFiles = postFileRepository.findByPostId(id);
        for (PostFile postFile : postFiles) {
            deleteFile(postFile);
        }
        postFiles.forEach(pf -> postFileRepository.deleteById(pf.getId()));
    }

    /**
     * 수정 시 유지하지 않을 첨부파일을 제거하고, DB에서도 삭제합니다.
     *
     * @param postId    게시글 ID
     * @param modifyDTO 수정 요청 DTO (보존할 파일명 포함)
     */
    public void removeDeletedFiles(Long postId, PostModifyRequest modifyDTO) {
        List<String> keepFilenames = modifyDTO.getPreserveFilenames();
        List<PostFile> postFiles = getFilesByPostId(postId);

        for (PostFile postFile : postFiles) {
            boolean shouldKeep = keepFilenames != null && keepFilenames.contains(postFile.getSavedFilename());
            if (shouldKeep) {
                continue;
            }
            deleteFile(postFile);
            postFileRepository.deleteById(postFile.getId());
        }
    }

    /**
     * 단일 첨부파일 삭제를 수행합니다.
     *
     * @param postFile 삭제할 첨부파일 엔티티
     */
    private void deleteFile(PostFile postFile) {
        fileService.deleteFile(postFile.getPath(), postFile.getSavedFilename());
    }

    /**
     * 파일을 브라우저에서 미리보기용으로 스트리밍합니다.
     *
     * @param savedFilename 저장된 파일명
     * @return 미리보기 ResponseEntity<Resource>
     */
    public ResponseEntity<Resource> preview(String savedFilename) {
        PostFile postFile = getPostFileBySavedName(savedFilename);
        return fileService.preview(postFile.getPath(), postFile.getSavedFilename(), postFile.getOriginalFilename());
    }
}

