package com.sparta.springthreeproject.board.entity;



import com.sparta.springthreeproject.user.entity.Users;
import com.sparta.springthreeproject.util.TimeStamped;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardLike extends TimeStamped {

    @Id
    @Column(name = "BOARDLIKES_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private Boolean isLiked;
//isLiked필요없을 수 있음

//    연결엔티티
    //컬럼을 줄여라! : 정규화

//    조회하는 쿼리 필요 있다면 save 없다면 delete
    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Users users;

//    public BoardLike(Board board, Users users) {
//        this.isLiked = true;
//        this.board = board;
//        this.users = users;
//        this.board.like();
//    }
//
//    @Builder
//    public BoardLike(Users users, Board board) {
//        this.users = users;
//        this.board = board;
//    }
//
//    public boolean likeLike() {
//        if(this.isLiked) {
//            this.isLiked = false;
//            this.board.disLike();
//            return this.isLiked;
//        }
//        this.isLiked = true;
//        this.board.like();
//        return this.isLiked;
//    }
}
