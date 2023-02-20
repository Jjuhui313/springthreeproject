package com.sparta.springthreeproject.board.service;

import com.sparta.springthreeproject.board.dto.BoardRequestDto;
import com.sparta.springthreeproject.board.dto.BoardResponseDto;
import com.sparta.springthreeproject.board.entity.Board;
import com.sparta.springthreeproject.board.dto.MessageDto;
import com.sparta.springthreeproject.board.repository.BoardRepository;
import com.sparta.springthreeproject.comment.service.CommentService;
import com.sparta.springthreeproject.exception.dto.ExcepMsg;
import com.sparta.springthreeproject.user.entity.UserRoleEnum;
import com.sparta.springthreeproject.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

import static com.sparta.springthreeproject.exception.message.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentService commentService;

    private boolean hasAuthority(Users users, Board board) {
        return users.getId().equals(board.getCreateBy()) || users.getRole().equals(UserRoleEnum.ADMIN);
    }
    public BoardResponseDto createBoard(BoardRequestDto requestDto, Users user) {
        Board board = new Board(requestDto, user);
        Board saveBoard = boardRepository.save(board);
        return new BoardResponseDto(saveBoard);
    }

    public List<BoardResponseDto> getPosts() {
        List<Board> board = boardRepository.findAllByOrderByCreateAtDesc().stream().filter(board1 -> !board1.getIsDeleted()).collect(Collectors.toList());
        return board.stream().map(board1 -> new BoardResponseDto(board1, commentService.getComment(board1.getId()))).collect(Collectors.toList());
    }

    public BoardResponseDto findById(Long id) {
        Board entity = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(BOARD_DOES_NOT_EXIEST.getMessage())
        );
        if (entity.getIsDeleted()) {
            throw new IllegalArgumentException(BOARD_HAS_BEEN_DELETED.getMessage());
        }
        return new BoardResponseDto(entity);
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
