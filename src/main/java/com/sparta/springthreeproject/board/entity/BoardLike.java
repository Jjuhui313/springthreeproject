package com.sparta.springthreeproject.board.entity;



import com.sparta.springthreeproject.user.entity.Users;
import com.sparta.springthreeproject.util.TimeStamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardLike extends TimeStamped {

    @Id
    @Column(name = "COMMENTLIKES_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isLiked;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Users users;

    public BoardLike(Board board, Users users) {
        this.isLiked = true;
        this.board = board;
        this.users = users;
        this.board.like();
    }

    @Builder
    public BoardLike(Users users, Board board) {
        this.users = users;
        this.board = board;
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
