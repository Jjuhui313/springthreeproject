package com.sparta.springthreeproject.comment.entity;

import com.sparta.springthreeproject.user.entity.Users;
import com.sparta.springthreeproject.util.TimeStamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike extends TimeStamped {
    @Id @Column(name = "COMMENTLIKES_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isLiked;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Users users;

    public CommentLike(Comment comment, Users users) {
        this.isLiked = true;
        this.comment = comment;
        this.users = users;
        this.comment.like();
    }

    public boolean likeLike() {
        if(this.isLiked) {
            this.isLiked = false;
            this.comment.disLike();
            return this.isLiked;
        }
        this.isLiked = true;
        this.comment.like();
        return this.isLiked;
    }
}
