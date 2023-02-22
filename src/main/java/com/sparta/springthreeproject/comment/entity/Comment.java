package com.sparta.springthreeproject.comment.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.springthreeproject.board.entity.Board;
import com.sparta.springthreeproject.comment.dto.CommentRequestDto;
import com.sparta.springthreeproject.user.entity.Users;
import com.sparta.springthreeproject.util.TimeStamped;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment extends TimeStamped {

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

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Users user;


    @OneToMany(mappedBy = "comment",fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<CommentLike> like;
    private Long totalLike = 0L;

    public Comment(CommentRequestDto commentRequestDto, Board board, Users users) {
        this.userName = users.getUserName();
        this.content = commentRequestDto.getContent();
        this.user = users;
        this.board = board;
        this.isDeleted = false;
    }


    public Comment update(String content) {
        this.content = content;
        return this;
    }

    public void setIsDeleted() {

        this.isDeleted = true;
    }

    public void like() {
        this.totalLike += 1;
    }
    public void disLike() {
        this.totalLike -= 1;
    }
}

