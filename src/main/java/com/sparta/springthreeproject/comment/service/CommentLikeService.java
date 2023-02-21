package com.sparta.springthreeproject.comment.service;

import com.sparta.springthreeproject.comment.entity.Comment;
import com.sparta.springthreeproject.comment.entity.CommentLike;
import com.sparta.springthreeproject.comment.repository.CommentLikeRepository;
import com.sparta.springthreeproject.comment.repository.CommentRepository;
import com.sparta.springthreeproject.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.springthreeproject.exception.message.ExceptionMessage.COMMENT_DOES_NOT_EXIEST;


@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public boolean commentLike(Long cId, Users user) {
        Optional<CommentLike> commentLike = commentLikeRepository.findByComment_IdAndUsers_id(cId, user.getId());

        if(commentLike.isEmpty()) {
            Comment comment = commentRepository.findById(cId).orElseThrow(
                    () -> new IllegalArgumentException(COMMENT_DOES_NOT_EXIEST.getMessage())
            );
            commentLikeRepository.save(new CommentLike(comment, user));
            return true;
        }
        CommentLike like = commentLike.get();
        return like.likeLike();
    }
}
