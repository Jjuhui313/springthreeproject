package com.sparta.springthreeproject.board.service;

import com.sparta.springthreeproject.board.dto.BoardRequestDto;
import com.sparta.springthreeproject.board.dto.BoardResponseDto;
import com.sparta.springthreeproject.board.entity.Board;
import com.sparta.springthreeproject.board.entity.MessageDto;
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

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentService commentService;
//    private final CommentService commentService;
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
                () -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
        );
        if (entity.getIsDeleted()) {
            throw new IllegalArgumentException("해당 게시글은 삭제된 게시글입니다.");
        }
        return new BoardResponseDto(entity);
    }

    @Transactional
    public MessageDto update(Long id, BoardRequestDto requestDto, Users user) throws IllegalAccessException {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );
        if(hasAuthority(user, board)) {
            board.update(requestDto);
            return new MessageDto(true, new BoardResponseDto(board));
        }
        throw new IllegalAccessException("작성자만 삭제/수정할 수 있습니다.");

    }

    @Transactional
    public ExcepMsg deleteBoard(Long id, Users user) throws IllegalAccessException {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );
        if (hasAuthority(user, board)) {
            board.delete(true);
            return new ExcepMsg("게시글 삭제 성공", OK.value());
        }
        throw new IllegalAccessException("작성자만 삭제/수정할 수 있습니다.");
    }

    private boolean hasAuthority(Users users, Board board) {
        return users.getUserName().equals(board.getUserName()) || users.getRole().equals(UserRoleEnum.ADMIN);
    }
}
