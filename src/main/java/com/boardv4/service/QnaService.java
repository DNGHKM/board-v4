package com.boardv4.service;

import com.boardv4.domain.Member;
import com.boardv4.domain.Qna;
import com.boardv4.dto.qna.QnaModifyRequest;
import com.boardv4.dto.qna.QnaViewResponse;
import com.boardv4.dto.qna.QnaWriteRequest;
import com.boardv4.dto.qna.QnaWriteResponse;
import com.boardv4.exception.base.ForbiddenException;
import com.boardv4.exception.qna.QnaNotFoundException;
import com.boardv4.mapper.QnaMapper;
import com.boardv4.repository.QnaRepository;
import com.boardv4.util.PasswordUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
     * QnA 게시글을 ID로 조회
     * <p>
     * 비밀글인 경우 작성자만 열람 가능
     *
     * @param qnaId    조회할 QnA 게시글 ID
     * @param username 현재 로그인한 사용자의 username (null 가능)
     * @return QnaViewResponse 게시글 상세 응답 DTO
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

        return qnaMapper.toViewDTO(qna, writer.getName(), answererName);
    }

    public QnaWriteResponse write(QnaWriteRequest writeDTO, String username) {
        Long writerId = memberService.getMemberByUsername(username).getId();

        Qna qna = Qna.builder()
                .writerId(writerId)
                .subject(writeDTO.getSubject())
                .content(writeDTO.getContent())
                .questionAt(LocalDateTime.now())
                .secret(writeDTO.isSecret())
                .password(PasswordUtil.encrypt(writeDTO.getPassword()))
                .viewCount(0)
                .build();

        qnaRepository.insert(qna);

        return QnaWriteResponse.builder().qnaId(qna.getId()).build();
    }

    public void modify(Long qnaId, QnaModifyRequest modifyDTO, String username) {
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
}
