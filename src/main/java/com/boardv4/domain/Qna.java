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
    private Integer viewCount;

    public boolean hasAnswer() {
        return !ObjectUtils.isEmpty(answer);
    }

    public void update(QnaModifyRequest modifyDTO) {
        this.subject = modifyDTO.getSubject();
        this.content = modifyDTO.getContent();
        this.secret = modifyDTO.isSecret();
    }
}
