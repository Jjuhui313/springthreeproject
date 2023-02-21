package com.sparta.springthreeproject.comment.repository;

import com.sparta.springthreeproject.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByComment_IdAndUsers_id(Long comment_id, Long User_id);
}
