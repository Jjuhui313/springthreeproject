package com.sparta.springthreeproject.comment.entity;

import com.sparta.springthreeproject.util.BasicEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike extends BasicEntity {
    @Id @Column(name = "COMMENTLIKES_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isLiked;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    public CommentLike(Comment comment) {
        this.isLiked = true;
        this.comment = comment;
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
