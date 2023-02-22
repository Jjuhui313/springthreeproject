package com.sparta.springthreeproject.board.dto;

import com.sparta.springthreeproject.board.entity.Board;
import com.sparta.springthreeproject.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {


    private String title;

    private String userName;

    private String content;

    private List<Comment> comment;

    private Long totalLike;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;


    public BoardResponseDto(Board entity) {
        this.title = entity.getTitle();
        this.userName = entity.getUserName();
        this.content = entity.getContent();
        this.comment = entity.getComment();
        this.createdAt = entity.getCreateAt();
        this.modifiedAt = entity.getModifiedAt();
        this.totalLike = entity.getTotalLike();
    }

    public BoardResponseDto(Board board, List<Comment> comments) {
        this.title = board.getTitle();
        this.userName = board.getUserName();
        this.comment = comments;
        this.content = board.getContent();
        this.createdAt = board.getCreateAt();
        this.modifiedAt = board.getModifiedAt();
        this.totalLike = board.getTotalLike();

    }


    public BoardResponseDto(BoardResponseDto board, List<Comment> comments) {
        this.title = board.getTitle();
        this.userName = board.getUserName();
        this.comment = comments;
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.totalLike = board.getTotalLike();
    }

    public static BoardResponseDto of(Board board) {
        return BoardResponseDto.builder()
                .createdAt(board.getCreateAt())
                .modifiedAt(board.getModifiedAt())
                .title(board.getTitle())
                .userName(board.getUserName())
                .content(board.getContent())
                .build();
    }
}
