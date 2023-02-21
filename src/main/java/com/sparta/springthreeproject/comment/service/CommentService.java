package com.sparta.springthreeproject.comment.service;

import com.sparta.springthreeproject.board.entity.Board;
import com.sparta.springthreeproject.board.repository.BoardRepository;
import com.sparta.springthreeproject.comment.dto.CommentRequestDto;
import com.sparta.springthreeproject.comment.entity.Comment;
import com.sparta.springthreeproject.comment.dto.CommentResponseDto;
import com.sparta.springthreeproject.comment.repository.CommentRepository;
import com.sparta.springthreeproject.exception.dto.ExcepMsg;
import com.sparta.springthreeproject.user.entity.UserRoleEnum;
import com.sparta.springthreeproject.user.entity.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

import static com.sparta.springthreeproject.exception.message.ExceptionMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    private boolean hasAuthority(Users users, Comment comment) {
        return comment.getUserName().equals(users.getUserName()) || users.getRole().equals(UserRoleEnum.ADMIN);
    }

    public CommentResponseDto createComment(Long bId, CommentRequestDto requestDto, Users user) {
        Board board = boardRepository.findById(bId).orElseThrow(
                () -> new IllegalArgumentException(BOARD_DOES_NOT_EXIEST.getMessage())
        );
        Comment comment = new Comment(requestDto, board, user.getUserName());
        commentRepository.save(comment);

        return CommentResponseDto.of(comment);
    }

    public List<Comment> getComment(Long id) {
        List<Comment> comments = commentRepository.findByBoard_IdOrderByCreateAtDesc(id);
        return comments.stream().filter(comment -> !comment.getIsDeleted()).collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto update(Long cId, CommentRequestDto requestDto, Users user) throws IllegalAccessException {
        Comment comment = commentRepository.findById(cId).orElseThrow();
        if(hasAuthority(user, comment)) {
            comment.update(requestDto.getContent());
            return new CommentResponseDto(comment);
        }
        throw new IllegalAccessException(ILLEGAL_ACCESS_UPDATE_OR_DELETE.getMessage());
    }

    @Transactional
    public ExcepMsg deleteComment(Long cId, Users user) throws IllegalAccessException{
        Comment comment = commentRepository.findById(cId).orElseThrow();

        if(hasAuthority(user, comment)) {
            comment.setIsDeleted();
            return new ExcepMsg("삭제 성공", OK.value());
        }
        throw new IllegalAccessException(ILLEGAL_ACCESS_UPDATE_OR_DELETE.getMessage());

    }
}
