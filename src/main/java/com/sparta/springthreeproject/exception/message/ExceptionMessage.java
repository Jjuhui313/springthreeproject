package com.sparta.springthreeproject.exception.message;

import lombok.Getter;

@Getter
public enum ExceptionMessage {

    BOARD_HAS_BEEN_DELETED("삭제된 게시물입니다."),
    BOARD_DOES_NOT_EXIEST("게시글이 존재하지 않습니다."),

    COMMENT_HAS_BEEN_DELETED("삭제된 댓글입니다."),
    COMMENT_DOES_NOT_EXIEST("댓글이 존재하지 않습니다."),

    ILLEGAL_ACCESS_UPDATE_OR_DELETE("작성자만 삭제/수정할 수 있습니다.");

    private final String message;
    ExceptionMessage(String message) {
        this.message = message;
    }
}
