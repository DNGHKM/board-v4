package com.boardv4.domain;

import com.boardv4.dto.qna.QnaModifyRequest;
import lombok.*;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Qna {
    private Long id;
    private Long writerId;
    private String subject;
    private String content;
    private LocalDateTime questionAt;
    private Long answererId;
    private String answer;
    private LocalDateTime answerAt;
    private boolean secret;
    private String password;
    private Integer viewCount;

    public boolean hasAnswer() {
        return !ObjectUtils.isEmpty(answer);
    }

    public void update(QnaModifyRequest modifyDTO) {
        this.subject = modifyDTO.getSubject();
        this.content = modifyDTO.getContent();

        //공개 -> 비공개로 전환 시에만 비밀번호 덮어씀
        if (!this.secret && modifyDTO.isSecret()) {
            this.password = modifyDTO.getPassword();
        }

        //비공개 -> 공개 전환 시 비밀번호 없앰
        if (this.secret && !modifyDTO.isSecret()) {
            this.password = null;
        }
        this.secret = modifyDTO.isSecret();
    }
}
