package com.boardv4.service;

import com.boardv4.domain.Board;
import com.boardv4.dto.board.BoardResponse;
import com.boardv4.exception.board.BoardNotFoundException;
import com.boardv4.mapper.BoardMapper;
import com.boardv4.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 게시판 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
@AllArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
    }

    public List<BoardResponse> getAllBoardList() {
        return boardRepository.findAll().stream()
                .map(boardMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BoardResponse getBoardInfoById(Long boardId) {
        Board board = getBoardById(boardId);
        return boardMapper.toDTO(board);
    }
}
