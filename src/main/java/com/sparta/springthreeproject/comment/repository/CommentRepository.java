package com.sparta.springthreeproject.comment.repository;

import com.sparta.springthreeproject.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoard_IdOrderByCreateAtDesc(Long id);
    Optional<Comment> findByIdAndUserName(Long id, String username);

}
