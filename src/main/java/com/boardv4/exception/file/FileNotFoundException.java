package com.boardv4.exception.file;

import com.boardv4.exception.base.NotFoundException;

public class FileNotFoundException extends NotFoundException {
    public FileNotFoundException(String savedFilename) {
        super("해당 파일을 찾을 수 없습니다. (" + savedFilename + ")");
    }
}