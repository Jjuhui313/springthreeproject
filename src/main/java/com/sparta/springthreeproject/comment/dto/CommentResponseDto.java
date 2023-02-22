package com.sparta.springthreeproject.comment.dto;

import com.sparta.springthreeproject.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private String content;
    private String userName;

    private Long totalLike;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment entity) {
        this.content = entity.getContent();
        this.userName = entity.getUserName();
        this.createAt = entity.getCreateAt();
        this.modifiedAt = entity.getModifiedAt();
        this.totalLike = entity.getTotalLike();
    }


    public static CommentResponseDto of(Comment comment) {
        return CommentResponseDto.builder()
                .content(comment.getContent())
                .userName(comment.getUserName())
                .createAt(comment.getCreateAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

    public static List<CommentResponseDto> of(Collection<Comment> entities) {
        return entities.stream().map(CommentResponseDto::of).collect(Collectors.toList());
    }
}
