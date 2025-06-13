package com.boardv4.service;

import com.boardv4.domain.PostFile;
import com.boardv4.dto.post.PostModifyRequest;
import com.boardv4.repository.PostFileRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostFileService {
    private final FileService fileService;
    private final PostFileRepository postFileRepository;

    public List<PostFile> getFilesByPostId(Long id) {
        return postFileRepository.findByPostId(id);
    }

    /**
     * 게시글에 첨부된 파일들을 저장
     *
     * <p>각 파일은 지정된 게시판(boardName)을 기준으로 하위 디렉토리에 저장되며,
     * 파일 메타데이터(PostFile)는 DB에 함께 저장</p>
     *
     * @param boardEngName 게시판 (경로용)
     * @param postId       게시글 ID
     * @param files        업로드된 첨부 파일 목록 (MultipartFile List)
     */
    /*
        TODO 초기에는 여기서 확장자를 준수하지 않는 파일만 빼고 나머지는 저장하는 것으로 구현하였으나,
            게시글 작성 실패 처리해도 무방하다고 판단 되어 로직 이동
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


    /**
     * 게시글 첨부파일 삭제
     *
     * <p>게시글 id를 기준으로 조회한 파일들을 삭제하고, DB에서도 정보를 삭제</p>
     *
     * @param id : 첨부파일 id
     */
    public void deleteByPostId(Long id) {
        List<PostFile> postFiles = postFileRepository.findByPostId(id);
        for (PostFile postFile : postFiles) {
            deleteFile(postFile);
        }
        postFiles.forEach(pf -> postFileRepository.deleteById(pf.getId()));
    }

    /**
     * 게시글 수정 시, 기존 첨부파일 중 클라이언트가 유지하지 않은 파일들을
     * 실제 파일 시스템과 DB에서 삭제합니다.
     *
     * <p>파일 삭제 도중 오류가 발생해도, 나머지 파일 삭제는 계속 진행됩니다.</p>
     *
     * @param postId    수정 대상 게시글의 ID
     * @param modifyDTO 클라이언트로부터 전달받은 수정 요청 DTO
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

    private void deleteFile(PostFile postFile) {
        fileService.deleteFile(postFile.getPath(), postFile.getSavedFilename());
    }

    public ResponseEntity<Resource> download(String savedFilename) {
        PostFile postFile = postFileRepository.findBySavedFilename(savedFilename);

        return fileService.downloadFile(postFile.getPath(), postFile.getSavedFilename(), postFile.getOriginalFilename());
    }
}

