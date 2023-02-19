package com.sparta.springthreeproject.board.controller;

import com.sparta.springthreeproject.board.dto.BoardRequestDto;
import com.sparta.springthreeproject.board.dto.BoardResponseDto;
import com.sparta.springthreeproject.board.entity.MessageDto;
import com.sparta.springthreeproject.board.service.BoardService;
import com.sparta.springthreeproject.comment.entity.Comment;
import com.sparta.springthreeproject.comment.service.CommentService;
import com.sparta.springthreeproject.exception.dto.ExcepMsg;
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
    public Object getPosts(@PathVariable Long id) {
        List<Comment> comments = commentService.getComment(id);
        BoardResponseDto board = null;

        try {
            board = boardService.findById(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ExcepMsg("해당 게시글은 삭제된 게시글입니다.", BAD_REQUEST.value()), BAD_REQUEST);
        }
        return new BoardResponseDto(board, comments);
    }

    @PatchMapping("/board/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MessageDto msg = null;
        try {
            msg = boardService.update(id, requestDto, userDetails.getUser());
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(new ExcepMsg("작성자만 삭제/수정할 수 있습니다.", BAD_REQUEST.value()), BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.", BAD_REQUEST.value()), BAD_REQUEST);
        }
        if (!msg.getSuccess()) {
            return new ResponseEntity<>(msg, UNAUTHORIZED);
        }
        return new ResponseEntity<>(msg, OK);
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ExcepMsg msg = null;
        try {
            msg = boardService.deleteBoard(id, userDetails.getUser());
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(new ExcepMsg("작성자만 삭제/수정할 수 있습니다.", BAD_REQUEST.value()), BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.", BAD_REQUEST.value()), BAD_REQUEST);
        }
        return new ResponseEntity<>(msg, OK);

    }
}
