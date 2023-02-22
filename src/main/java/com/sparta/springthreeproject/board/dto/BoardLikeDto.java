package com.sparta.springthreeproject.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardLikeDto {
    private boolean likeBool;
    private Long likeCnt;
}
