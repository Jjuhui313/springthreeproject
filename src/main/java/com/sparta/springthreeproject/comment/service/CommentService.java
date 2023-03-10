package com.sparta.springthreeproject.comment.service;

import com.sparta.springthreeproject.board.entity.Board;
import com.sparta.springthreeproject.board.repository.BoardRepository;
import com.sparta.springthreeproject.comment.dto.CommentRequestDto;
import com.sparta.springthreeproject.comment.entity.Comment;
import com.sparta.springthreeproject.comment.dto.CommentResponseDto;
import com.sparta.springthreeproject.comment.repository.CommentLikeRepository;
import com.sparta.springthreeproject.comment.repository.CommentRepository;
import com.sparta.springthreeproject.exception.dto.ExcepMsg;
import com.sparta.springthreeproject.user.entity.UserRoleEnum;
import com.sparta.springthreeproject.user.entity.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

import static com.sparta.springthreeproject.exception.message.ExceptionMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentLikeRepository commentLikeRepository;
    private final BoardRepository boardRepository;

    private boolean hasAuthority(Users users, Comment comment) {
        return comment.getUserName().equals(users.getUserName()) || users.getRole().equals(UserRoleEnum.ADMIN);
    }

    public CommentResponseDto createComment(Long bId, CommentRequestDto requestDto, Users user) {
        Board board = boardRepository.findById(bId).orElseThrow(
                () -> new IllegalArgumentException(BOARD_DOES_NOT_EXIEST.getMessage())
        );
        Comment comment = new Comment(requestDto, board, user);
        commentRepository.save(comment);

        return CommentResponseDto.of(comment);
    }



    public List<Comment> getComment(Long id) {
        List<Comment> comments = commentRepository.findByBoard_IdOrderByCreateAtDesc(id);
        List<CommentResponseDto> responseDtos = new ArrayList<>();
        for(Comment comment : comments) {
            Long likeTotal = commentLikeRepository.countAllByComment_Id(comment.getId());
            CommentResponseDto responseDto = CommentResponseDto.builder()
                    .content(comment.getContent())
                    .userName(comment.getUserName())
                    .createAt(comment.getCreateAt())
                    .modifiedAt(comment.getModifiedAt())
                    .totalLike(likeTotal)
                    .build();
            responseDtos.add(responseDto);
        }

//        return responseDtos.stream().map(responseDtos1 -> new Comment(responseDtos1, ));
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
            return new ExcepMsg("?????? ??????", OK.value());
        }
        throw new IllegalAccessException(ILLEGAL_ACCESS_UPDATE_OR_DELETE.getMessage());

    }
}
