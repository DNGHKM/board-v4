package com.boardv4.service;

import com.boardv4.domain.Member;
import com.boardv4.domain.Qna;
import com.boardv4.dto.qna.*;
import com.boardv4.exception.base.ForbiddenException;
import com.boardv4.exception.qna.QnaNotFoundException;
import com.boardv4.mapper.QnaMapper;
import com.boardv4.repository.QnaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * QnA 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * 작성, 조회, 수정, 삭제 등의 기능을 제공합니다.
 */
@Service
@AllArgsConstructor
@Slf4j
public class QnaService {
    private final QnaRepository qnaRepository;
    private final MemberService memberService;
    private final QnaMapper qnaMapper;

    public Qna getQnaById(Long id) {
        return qnaRepository.findById(id)
                .orElseThrow(() -> new QnaNotFoundException(id));
    }

    /**
     * QnA 목록(페이징/검색 조건 포함)을 조회합니다.
     *
     * @param request  검색 조건
     * @param username 현재 로그인한 사용자명 (nullable) - 나의 문의내역 찾기에서 사용
     * @return QnA 목록 응답
     */
    public QnaListResponse getQnaList(QnaSearchRequest request, String username) {
        // 검색 결과 개수 조회
        int totalCount = qnaRepository.countBySearch(request, username);

        // 전체 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / request.getSize());

        //페이지 수 보정(1 미만 혹은 너무 큰 페이지 넘버가 들어오는 경우)
        request.adjustPage(totalPages);

        // 페이징 계산
        int offset = Math.min(
                (request.getPage() - 1) * request.getSize(),
                (totalCount / request.getSize()) * request.getSize()
        );

        // 게시글 목록 조회
        List<QnaSummaryResponse> qnaSummaryList = qnaRepository.findBySearch(request, offset, username);

        // 응답 DTO 구성
        return QnaListResponse.from(request, totalPages, totalCount, qnaSummaryList);
    }

    /**
     * 최신 QnA 게시글을 제한 개수만큼 조회합니다.
     *
     * @param limit 최대 개수
     * @return QnA 목록
     */
    public List<QnaSummaryResponse> getLatestQnaList(Integer limit) {
        return qnaRepository.findLatestQnaList(limit);
    }

    /**
     * QnA 게시글 상세 정보를 조회합니다.
     * 비밀글의 경우 작성자만 접근할 수 있습니다.
     *
     * @param qnaId    게시글 ID
     * @param username 현재 로그인한 사용자명 (nullable)
     * @return QnA 상세 응답 DTO
     * @throws ForbiddenException 비밀글이면서 작성자가 아닌 경우
     */
    public QnaViewResponse getQnaViewById(Long qnaId, String username) {
        Qna qna = getQnaById(qnaId);
        Member writer = memberService.getMemberById(qna.getWriterId());

        if (qna.isSecret() && !username.equals(writer.getUsername())) {
            throw new ForbiddenException();
        }

        String answererName = null;
        if (qna.getAnswererId() != null) {
            answererName = memberService.getMemberById(qna.getAnswererId()).getName();
        }

        return qnaMapper.toViewDTO(qna, writer.getName(), writer.getUsername(), answererName);
    }

    /**
     * QnA 게시글을 작성합니다.
     *
     * @param writeDTO 작성 정보
     * @param username 작성자 ID
     * @return 작성된 QnA ID 응답
     */
    public QnaWriteResponse write(QnaWriteRequest writeDTO, String username) {
        Long writerId = memberService.getMemberByUsername(username).getId();

        Qna qna = Qna.builder()
                .writerId(writerId)
                .subject(writeDTO.getSubject())
                .content(writeDTO.getContent())
                .questionAt(LocalDateTime.now())
                .secret(writeDTO.isSecret())
                .viewCount(0)
                .build();

        qnaRepository.insert(qna);

        return QnaWriteResponse.builder().qnaId(qna.getId()).build();
    }

    /**
     * QnA 게시글을 수정합니다.
     * 답변이 달린 경우 수정할 수 없습니다.
     *
     * @param qnaId     게시글 ID
     * @param modifyDTO 수정 정보
     * @param username  요청자 ID
     * @return 수정된 QnA ID 응답
     * @throws ForbiddenException 답변이 달려 있거나 본인이 아닌 경우
     */
    public QnaModifyResponse modify(Long qnaId, QnaModifyRequest modifyDTO, String username) {
        Qna qna = getQnaById(qnaId);

        //답변이 달린 경우 수정 불가
        if (qna.hasAnswer()) {
            throw new ForbiddenException();
        }

        //본인이 작성하지 않았으면 수정 불가
        Member writer = memberService.getMemberById(qna.getWriterId());
        if (!writer.getUsername().equals(username)) {
            throw new ForbiddenException();
        }
        qna.update(modifyDTO);
        qnaRepository.update(qna);

        return QnaModifyResponse.builder().qnaId(qna.getId()).build();
    }

    public void delete(Long qnaId, String username) {
        Qna qna = getQnaById(qnaId);

        //답변이 달린 경우 삭제 불가
        if (qna.hasAnswer()) {
            throw new ForbiddenException();
        }

        //본인이 작성하지 않았으면 삭제 불가
        Member writer = memberService.getMemberById(qna.getWriterId());
        if (!writer.getUsername().equals(username)) {
            throw new ForbiddenException();
        }

        qnaRepository.delete(qna.getId());
    }

    public Integer increaseViewCount(Long qnaId) {
        qnaRepository.increaseViewCount(qnaId);
        return qnaRepository.findViewCountById(qnaId);
    }
}
