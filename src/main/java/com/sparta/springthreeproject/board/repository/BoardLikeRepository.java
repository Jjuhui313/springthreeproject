package com.sparta.springthreeproject.board.repository;

import com.sparta.springthreeproject.board.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByBoard_IdAndUsers_Id(Long board_id, Long user_id);
}
