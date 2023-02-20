package com.sparta.springthreeproject.board.entity;



import com.sparta.springthreeproject.util.BasicEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class BoardLike extends BasicEntity {

    @Id
    @Column(name = "COMMENTLIKES_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isLiked;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private Board board;

    public BoardLike(Board board) {
        this.isLiked = true;
        this.board = board;
        this.board.like();
    }

    public boolean likeLike() {
        if(this.isLiked) {
            this.isLiked = false;
            this.board.disLike();
            return this.isLiked;
        }
        this.isLiked = true;
        this.board.like();
        return this.isLiked;
    }
}
