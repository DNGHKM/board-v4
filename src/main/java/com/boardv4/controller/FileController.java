package com.boardv4.controller;

import com.boardv4.controller.doc.FileApiDoc;
import com.boardv4.service.PostFileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/files")
@AllArgsConstructor
@Slf4j
public class FileController implements FileApiDoc {

    private final PostFileService postFileService;

    @GetMapping("/{savedFilename}")
    public ResponseEntity<Resource> getPostView(@PathVariable String savedFilename) {
        return postFileService.download(savedFilename);
    }

    @GetMapping("/preview/{savedFilename}")
    public ResponseEntity<Resource> preview(@PathVariable String savedFilename) {
        return postFileService.preview(savedFilename);
    }
}
