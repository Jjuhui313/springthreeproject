package com.sparta.springthreeproject.board.service;

import com.sparta.springthreeproject.board.dto.BoardLikeDto;
import com.sparta.springthreeproject.board.entity.Board;
import com.sparta.springthreeproject.board.entity.BoardLike;
import com.sparta.springthreeproject.board.repository.BoardLikeRepository;
import com.sparta.springthreeproject.board.repository.BoardRepository;
import com.sparta.springthreeproject.user.entity.Users;
import com.sparta.springthreeproject.user.repository.UserRepository;
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

    private final UserRepository userRepository;

    @Transactional
    public BoardLikeDto boardLike(Long id, Users user) {
        Users users = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException(COULD_NOT_FOUND_USER.getMessage())
        );
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(BOARD_DOES_NOT_EXIEST.getMessage())
        );
        BoardLike like = BoardLike.builder()
                .board(board)
                .users(users)
                .build();

        if(boardLikeRepository.findByBoard_IdAndUsers_Id(board.getId(), users.getId()).orElse(null) == null) {
            boardLikeRepository.save(like);
            Long cnt = boardLikeRepository.countAllByBoardId(board.getId());
            return BoardLikeDto.builder()
                    .likeBool(true)
                    .likeCnt(cnt)
                    .build();
        } else {
            boardLikeRepository.deleteByBoard_IdAndUsers_id(board.getId(), user.getId());
            Long cnt = boardLikeRepository.countAllByBoardId(board.getId());
            return BoardLikeDto.builder()
                    .likeBool(false)
                    .likeCnt(cnt)
                    .build();
        }
    }



//    public boolean boardLike(Long id, Users user) {
//        Optional<BoardLike> boardLike = boardLikeRepository.findByBoard_IdAndUsers_Id(id, user.getId());
//
//        if (boardLike.isEmpty()) {
//            Board board = boardRepository.findById(id).orElseThrow(
//                    () -> new IllegalArgumentException(BOARD_DOES_NOT_EXIEST.getMessage())
//            );
//            boardLikeRepository.save(new BoardLike(board, user));
//            return true;
//        }
//        BoardLike like = boardLike.get();
//        return like.likeLike();
//    }

//    @Transactional
//    public void insertLike(Long id, Users user) {
//        Users users = userRepository.findById(user.getId()).orElseThrow(
//                () -> new IllegalArgumentException(COULD_NOT_FOUND_USER.getMessage())
//        );
//        Board board = boardRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException(BOARD_DOES_NOT_EXIEST.getMessage())
//        );
//        if(boardLikeRepository.findByBoard_IdAndUsers_Id(users.getId(), board.getId()).isPresent()) {
//            throw new IllegalArgumentException(ALREADY_EXIST_DATA.getMessage());
//        }
//        BoardLike boardLike = BoardLike.builder()
//                .board(board)
//                .users(user)
//                .build();
//
//        boardLikeRepository.save(boardLike);
//        boardRepository.addLikeCount(board);
//    }


}
