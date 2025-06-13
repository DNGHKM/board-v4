package com.boardv4.service;

import com.boardv4.domain.Board;
import com.boardv4.exception.board.BoardNotFoundException;
import com.boardv4.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
    }
}
