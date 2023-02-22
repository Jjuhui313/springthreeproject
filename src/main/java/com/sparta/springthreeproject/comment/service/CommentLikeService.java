package com.sparta.springthreeproject.comment.service;

import com.sparta.springthreeproject.comment.dto.CommentLikeDto;
import com.sparta.springthreeproject.comment.entity.Comment;
import com.sparta.springthreeproject.comment.entity.CommentLike;
import com.sparta.springthreeproject.comment.repository.CommentLikeRepository;
import com.sparta.springthreeproject.comment.repository.CommentRepository;
import com.sparta.springthreeproject.user.entity.Users;
import com.sparta.springthreeproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.springthreeproject.exception.message.ExceptionMessage.*;


@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    @Transactional
    public CommentLikeDto commentLike(Long cId, Users user) {

        Users users = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException(COULD_NOT_FOUND_USER.getMessage())
        );
        Comment comment = commentRepository.findById(cId).orElseThrow(
                () -> new IllegalArgumentException(COMMENT_DOES_NOT_EXIEST.getMessage())
        );



        CommentLike like = CommentLike.builder()
                .comment(comment)
                .users(users)
                .build();

        if(commentLikeRepository.findByComment_IdAndUsers_id(comment.getId(),user.getId()).orElse(null) == null) {
            commentLikeRepository.save(like);
//            Long cnt = commentLikeRepository.countAllByComment_Id(comment.getId()) + 1;
            comment.like();
            return CommentLikeDto.builder()
                    .likeBool(true)
                    .likeCnt(comment.getTotalLike())
                    .build();
        } else {
            commentLikeRepository.deleteByComment_IdAndUsers_Id(comment.getId(), user.getId());
//            Long cnt = commentLikeRepository.countAllByComment_Id(comment.getId()) - 1;
            comment.disLike();
            return CommentLikeDto.builder()
                    .likeBool(false)
                    .likeCnt(comment.getTotalLike())
                    .build();
        }

    }


//    public boolean commentLike(Long cId, Users user) {
//        Optional<CommentLike> commentLike = commentLikeRepository.findByComment_IdAndUsers_id(cId, user.getId());
//
//        if(commentLike.isEmpty()) {
//            Comment comment = commentRepository.findById(cId).orElseThrow(
//                    () -> new IllegalArgumentException(COMMENT_DOES_NOT_EXIEST.getMessage())
//            );
//            commentLikeRepository.save(new CommentLike(comment, user));
//            return true;
//        }
//        CommentLike like = commentLike.get();
//        return like.likeLike();
//    }
}
