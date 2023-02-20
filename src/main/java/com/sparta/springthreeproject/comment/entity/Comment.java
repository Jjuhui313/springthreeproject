package com.sparta.springthreeproject.comment.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.springthreeproject.board.entity.Board;
import com.sparta.springthreeproject.comment.dto.CommentRequestDto;
import com.sparta.springthreeproject.util.BasicEntity;
//import com.sparta.springthreeproject.util.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BasicEntity {

    @Id
    @Column(name = "COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    @Column(nullable = false)
    private String content;

    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Board board;

    private Integer totalLike;

    public Comment(CommentRequestDto commentRequestDto, Board board, String userName) {
        this.userName = userName;
        this.content = commentRequestDto.getContent();
        this.board = board;
        this.isDeleted = false;
        this.totalLike = 0;
    }


    public Comment update(String content) {
        this.content = content;
        return this;
    }

    public void setIsDeleted() {

        this.isDeleted = true;
    }

    protected void like() {
        this.totalLike += 1;
    }
    protected void disLike() {
        this.totalLike -= 1;
    }
}

