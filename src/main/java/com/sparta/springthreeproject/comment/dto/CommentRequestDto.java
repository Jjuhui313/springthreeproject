package com.sparta.springthreeproject.comment.dto;

import com.sparta.springthreeproject.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private String content;

    public CommentRequestDto(Comment comment) {

        this.content = comment.getContent();
    }
}
