package com.sparta.springthreeproject.board.controller;

import com.sparta.springthreeproject.board.dto.BoardRequestDto;
import com.sparta.springthreeproject.board.dto.BoardResponseDto;
import com.sparta.springthreeproject.board.dto.MessageDto;
import com.sparta.springthreeproject.board.service.BoardLikeService;
import com.sparta.springthreeproject.board.service.BoardService;
import com.sparta.springthreeproject.comment.entity.Comment;
import com.sparta.springthreeproject.comment.service.CommentService;
import com.sparta.springthreeproject.exception.dto.ExcepMsg;
import com.sparta.springthreeproject.jwt.JwtUtil;
import com.sparta.springthreeproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    private final BoardLikeService boardLikeService;

    @PostMapping("/board")
    public ResponseEntity<Object> createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardResponseDto boardResponseDto = null;
        try {
            boardResponseDto = boardService.createBoard(requestDto, userDetails.getUser());
            if(boardResponseDto == null) {
                return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.",BAD_REQUEST.value()), BAD_REQUEST);
            } else {
                return new ResponseEntity<>(boardResponseDto, OK);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.", BAD_REQUEST.value()), BAD_REQUEST);
        }
    }

    @GetMapping("/board")
    public List<BoardResponseDto> getPosts() {

        return boardService.getPosts();
    }

    @GetMapping("/board/{id}")
    public BoardResponseDto getPosts(@PathVariable Long id) {
        List<Comment> comments = commentService.getComment(id);
        BoardResponseDto board = boardService.findById(id);

//        try {
//            board = boardService.findById(id);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(new ExcepMsg("해당 게시글은 삭제된 게시글입니다.", BAD_REQUEST.value()), BAD_REQUEST);
//        }
        return new BoardResponseDto(board, comments);
    }

    @PatchMapping("/board/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        MessageDto msg = boardService.update(id, requestDto, userDetails.getUser());
//        try {
//            msg = boardService.update(id, requestDto, userDetails.getUser());
//        } catch (IllegalAccessException e) {
//            return new ResponseEntity<>(new ExcepMsg("작성자만 삭제/수정할 수 있습니다.", BAD_REQUEST.value()), BAD_REQUEST);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.", BAD_REQUEST.value()), BAD_REQUEST);
//        }
//        if (!msg.getSuccess()) {
//            return new ResponseEntity<>(msg, UNAUTHORIZED);
//        }
        return new ResponseEntity<>(msg, OK);
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        ExcepMsg msg = boardService.deleteBoard(id, userDetails.getUser());
//        try {
//            msg = boardService.deleteBoard(id, userDetails.getUser());
//        } catch (IllegalAccessException e) {
//            return new ResponseEntity<>(new ExcepMsg("작성자만 삭제/수정할 수 있습니다.", BAD_REQUEST.value()), BAD_REQUEST);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.", BAD_REQUEST.value()), BAD_REQUEST);
//        }
        return new ResponseEntity<>(msg, OK);

    }

    @PostMapping("/board/{id}/like")
    public ResponseEntity<Object> likeBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean isLiked = boardLikeService.boardLike(id, userDetails.getUser());
        ExcepMsg msg = null;

        if(isLiked) {
            msg = new ExcepMsg("좋아요", OK.value());
        }
        msg = new ExcepMsg("좋아요 취소", OK.value());
        return new ResponseEntity<>(msg, OK);
    }
//    @PostMapping("/board/{id}/like")
//    public ResponseEntity<Object> insertLikeBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        boardLikeService.insertLike(id, userDetails.getUser());
//        ExcepMsg msg = new ExcepMsg("좋아요", OK.value());
//        return new ResponseEntity<>(msg, OK);
//    }
//    @DeleteMapping("/board/{id}/like")
//    public ResponseEntity<Object> deleteLikeBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        boardLikeService.deleteLike(id, userDetails.getUser());
//        ExcepMsg msg = new ExcepMsg("좋아요 취소", OK.value());
//        return new ResponseEntity<>(msg, OK);
//    }





}
