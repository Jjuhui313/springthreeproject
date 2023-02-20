package com.sparta.springthreeproject.board.repository;

import com.sparta.springthreeproject.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByCreateAtDesc();
//    Board findByTitle(String title);
}
