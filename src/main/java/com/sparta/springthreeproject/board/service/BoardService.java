package com.sparta.springthreeproject.board.service;

import com.sparta.springthreeproject.board.dto.BoardRequestDto;
import com.sparta.springthreeproject.board.dto.BoardResponseDto;
import com.sparta.springthreeproject.board.entity.Board;
import com.sparta.springthreeproject.board.dto.MessageDto;
import com.sparta.springthreeproject.board.repository.BoardLikeRepository;
import com.sparta.springthreeproject.board.repository.BoardRepository;
import com.sparta.springthreeproject.comment.entity.Comment;
import com.sparta.springthreeproject.comment.service.CommentService;
import com.sparta.springthreeproject.exception.dto.ExcepMsg;
import com.sparta.springthreeproject.user.entity.UserRoleEnum;
import com.sparta.springthreeproject.user.entity.Users;
import com.sparta.springthreeproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

import static com.sparta.springthreeproject.exception.message.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final BoardLikeRepository boardLikeRepository;
    private final CommentService commentService;

    private final UserRepository userRepository;

    private boolean hasAuthority(Users users, Board board) {
        return users.getId().equals(board.getUser().getId()) || users.getRole().equals(UserRoleEnum.ADMIN);
    }
    public BoardResponseDto createBoard(BoardRequestDto requestDto, Users user) {
        Board board = new Board(requestDto, user);
        Board saveBoard = boardRepository.save(board);
        return new BoardResponseDto(saveBoard);
    }

    public List<BoardResponseDto> getPosts() {
        List<Board> boards = boardRepository.findAllByOrderByCreateAtDesc().stream().filter(board1 -> !board1.getIsDeleted()).collect(Collectors.toList());
        List<BoardResponseDto> responseDtos = new ArrayList<>();

        for(Board board : boards) {
            Long likeTotal = boardLikeRepository.countAllByBoard_Id(board.getId());
            List<Comment> comments = commentService.getComment(board.getId());
            BoardResponseDto boardResponseDto = BoardResponseDto.builder()
                    .title(board.getTitle())
                    .userName(board.getUserName())
                    .content(board.getContent())
                    .totalLike(likeTotal)
                    .comment(comments)
                    .createdAt(board.getCreateAt())
                    .modifiedAt(board.getModifiedAt())
                    .build();
            responseDtos.add(boardResponseDto);
        }
        return responseDtos;

//        return boards.stream().map(board1 -> new BoardResponseDto(board1, commentService.getComment(board1.getId()))).collect(Collectors.toList());
    }

    public BoardResponseDto findById(Long id) {
        Board entity = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(BOARD_DOES_NOT_EXIEST.getMessage())
        );
        if (entity.getIsDeleted()) {
            throw new IllegalArgumentException(BOARD_HAS_BEEN_DELETED.getMessage());
        }

        Users users = userRepository.findByUserName(entity.getUserName()).orElseThrow(
                () -> new IllegalArgumentException("유효한 회원을 찾을 수 없습니다.")
        );

//        Long like = 0L;
////        Long like = boardLikeRepository.countAllByBoard_Id(entity.getId());
//        if(boardLikeRepository.findByBoard_IdAndUsers_Id(id, users.getId()).orElse(null) == null) {
//            like = boardLikeRepository.countAllByBoard_Id(id);
//        }
        Long likeTotal = boardLikeRepository.countAllByBoard_Id(id);


        return BoardResponseDto.builder()
                .title(entity.getTitle())
                .content(entity.getContent())
                .userName(entity.getUserName())
                .totalLike(likeTotal)
                .createdAt(entity.getCreateAt())
                .modifiedAt(entity.getModifiedAt())
                .comment(entity.getComment())
                .build();
    }

    @Transactional
    public MessageDto update(Long id, BoardRequestDto requestDto, Users user) throws IllegalAccessException {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(BOARD_DOES_NOT_EXIEST.getMessage())
        );
        if(hasAuthority(user, board)) {
            board.update(requestDto);
            return new MessageDto(true, new BoardResponseDto(board));
        }
        throw new IllegalAccessException(ILLEGAL_ACCESS_UPDATE_OR_DELETE.getMessage());

    }

    @Transactional
    public ExcepMsg deleteBoard(Long id, Users user) throws IllegalAccessException {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(BOARD_DOES_NOT_EXIEST.getMessage())
        );
        if (hasAuthority(user, board)) {
            board.delete(true);
            return new ExcepMsg("게시글 삭제 성공", OK.value());
        }
        throw new IllegalAccessException(ILLEGAL_ACCESS_UPDATE_OR_DELETE.getMessage());
    }
}
