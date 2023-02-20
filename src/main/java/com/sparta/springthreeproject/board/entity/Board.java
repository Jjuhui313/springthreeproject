package com.sparta.springthreeproject.board.entity;

import com.sparta.springthreeproject.board.dto.BoardRequestDto;
import com.sparta.springthreeproject.comment.entity.Comment;
import com.sparta.springthreeproject.user.entity.Users;
import com.sparta.springthreeproject.util.BasicEntity;
//import com.sparta.springthreeproject.util.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BasicEntity {
    @Id @Column(name = "BOARD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Users user;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    private List<Comment> comment = new ArrayList<>();

    private Integer totalLike;

    public Board(BoardRequestDto requestDto, Users user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.user = user;
        this.isDeleted = false;
        this.totalLike = 0;
    }


    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public String getUserName() {

        return this.user.getUserName();
    }

    public void delete(Boolean deleted) {

        this.isDeleted = deleted;
    }

    protected void like() {
        this.totalLike += 1;
    }

    protected  void disLike() {
        this.totalLike -= 1;
    }

}
