package com.sparta.springthreeproject.comment.repository;

import com.sparta.springthreeproject.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByComment_IdAndCreateBy(Long CommentId, Long createBy);
}
