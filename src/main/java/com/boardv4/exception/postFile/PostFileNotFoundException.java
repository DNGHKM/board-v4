package com.boardv4.exception.postFile;

import com.boardv4.exception.base.NotFoundException;

public class PostFileNotFoundException extends NotFoundException {
    public PostFileNotFoundException(String savedFilename) {
        super("해당 파일을 찾을 수 없습니다. (" + savedFilename + ")");
    }
}