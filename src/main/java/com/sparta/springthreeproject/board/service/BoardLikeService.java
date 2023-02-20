package com.sparta.springthreeproject.board.service;

import com.sparta.springthreeproject.board.entity.Board;
import com.sparta.springthreeproject.board.entity.BoardLike;
import com.sparta.springthreeproject.board.repository.BoardLikeRepository;
import com.sparta.springthreeproject.board.repository.BoardRepository;
import com.sparta.springthreeproject.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import static com.sparta.springthreeproject.exception.message.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class BoardLikeService {
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public boolean boardLike(Long id, Users user) {
        Optional<BoardLike> boardLike = boardLikeRepository.findByBoard_IdAndCreateBy(id, user.getId());

        if (boardLike.isEmpty()) {
            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException(BOARD_DOES_NOT_EXIEST.getMessage())
            );
            boardLikeRepository.save(new BoardLike(board));
            return true;
        }
        BoardLike like = boardLike.get();
        return like.likeLike();
    }
}
