package com.sparta.springthreeproject.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentLikeDto {
    private boolean likeBool;
    private Long likeCnt;
}
