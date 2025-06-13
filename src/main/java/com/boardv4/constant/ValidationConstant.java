package com.boardv4.constant;

public interface ValidationConstant {
    String MEMBER_USERNAME_NOT_BLANK = "아이디를 입력해 주세요";
    int MEMBER_USERNAME_MIN_LENGTH = 4;
    int MEMBER_USERNAME_MAX_LENGTH = 11;
    String MEMBER_USERNAME_LENGTH = "아이디는 " + MEMBER_USERNAME_MIN_LENGTH + "자 이상 " + MEMBER_USERNAME_MAX_LENGTH + " 이하로 입력해 주세요";

    String MEMBER_PW_NOT_BLANK = "비밀번호를 입력해주세요";
    int MEMBER_PW_MIN_LENGTH = 4;
    int MEMBER_PW_MAX_LENGTH = 11;
    String MEMBER_PW_REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9_-]{"
            + MEMBER_PW_MIN_LENGTH + "," + MEMBER_PW_MAX_LENGTH + "}$";
    String MEMBER_PW_LENGTH = "비밀번호는 " + MEMBER_PW_MIN_LENGTH + "자 이상 " + MEMBER_PW_MAX_LENGTH + " 이하로 입력해 주세요";
    String MEMBER_PW_PATTERN = "비밀번호는 영문과 숫자를 모두 포함하며, '-'와 '_'를 제외한 특수문자는 사용할 수 없습니다.";

    String PW_CHK_NOT_BLANK = "비밀번호 확인을 입력해주세요";
    String MEMBER_PW_CHK_LENGTH = "비밀번호 확인은 " + MEMBER_PW_MIN_LENGTH + "자 이상 " + MEMBER_PW_MAX_LENGTH + " 이하로 입력해 주세요";
    String MEMBER_PW_CHK_PATTERN = "비밀번호 확인은 영문과 숫자를 모두 포함하며, '-'와 '_'를 제외한 특수문자는 사용할 수 없습니다.";

    String MEMBER_NAME_NOT_BLANK = "이름을 입력해 주세요";
    int MEMBER_NAME_MIN_LENGTH = 2;
    int MEMBER_NAME_MAX_LENGTH = 4;
    String MEMBER_NAME_LENGTH = "이름은 " + MEMBER_NAME_MIN_LENGTH + "자 이상 " + MEMBER_NAME_MAX_LENGTH + " 이하로 입력해 주세요";

    String BOARD_NOT_NULL = "게시판을 선택해 주세요";
    String CATEGORY_NOT_BLANK = "카테고리를 선택해 주세요";

    String SUBJECT_NOT_BLANK = "제목을 입력 해 주세요";
    String CONTENT_NOT_BLANK = "내용을 입력 해 주세요";
    int SUBJECT_MIN_LENGTH = 4;
    int SUBJECT_MAX_LENGTH = 100;
    String SUBJECT_LENGTH = "제목은 " + SUBJECT_MIN_LENGTH + "자 이상 " + SUBJECT_MAX_LENGTH + " 이하로 입력해 주세요";
    int CONTENT_MIN_LENGTH = 4;
    int CONTENT_MAX_LENGTH = 2000;
    String CONTENT_LENGTH = "제목은 " + CONTENT_MIN_LENGTH + "자 이상 " + CONTENT_MAX_LENGTH + " 이하로 입력해 주세요";
    int QNA_PW_MIN_LENGTH = 4;
    int QNA_PW_MAX_LENGTH = 4;
    String QNA_PW_LENGTH = "게시글 비밀번호는 " + QNA_PW_MIN_LENGTH + "자 이상 " + QNA_PW_MAX_LENGTH + " 이하로 입력해 주세요";
}
