package com.sparta.springthreeproject.board.repository;

import com.sparta.springthreeproject.board.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByBoard_IdAndCreateBy(Long BoardId, Long createdBy);
}
