package com.sparta.springthreeproject.exception.message;

import lombok.Getter;

@Getter
public enum ExceptionMessage {

    BOARD_HAS_BEEN_DELETED("삭제된 게시물입니다."),
    BOARD_DOES_NOT_EXIEST("게시글이 존재하지 않습니다."),

    COMMENT_HAS_BEEN_DELETED("삭제된 댓글입니다."),
    COMMENT_DOES_NOT_EXIEST("댓글이 존재하지 않습니다."),

    COULD_NOT_FOUND_USER("user를 찾을 수 없습니다."),

    ALREADY_EXIST_DATA("이미 처리된 요청입니다."),

    COULD_NOT_FOUND_LIKE("좋아요를 취소할 수 없습니다."),

    ILLEGAL_ACCESS_UPDATE_OR_DELETE("작성자만 삭제/수정할 수 있습니다.");

    private final String message;
    ExceptionMessage(String message) {
        this.message = message;
    }
}
