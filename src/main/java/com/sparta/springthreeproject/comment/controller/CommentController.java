package com.sparta.springthreeproject.comment.controller;

import com.sparta.springthreeproject.comment.dto.CommentRequestDto;
import com.sparta.springthreeproject.comment.dto.CommentResponseDto;
import com.sparta.springthreeproject.comment.service.CommentLikeService;
import com.sparta.springthreeproject.comment.service.CommentService;
import com.sparta.springthreeproject.exception.dto.ExcepMsg;
import com.sparta.springthreeproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentLikeService commentLikeService;

    @PostMapping("/board/{b-id}/comment")
    public ResponseEntity<Object> createComment(@PathVariable(name = "b-id") Long bId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto commentResponseDto = null;
        try {
            commentResponseDto = commentService.createComment(bId, requestDto, userDetails.getUser());
            if(commentResponseDto == null) {
                return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.", BAD_REQUEST.value()), BAD_REQUEST);
            } else {
                return new ResponseEntity<>(commentResponseDto, OK);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.", BAD_REQUEST.value()), BAD_REQUEST);
        }

    }

    @PatchMapping("/board/{b-id}/comment/{c-id}")
    public ResponseEntity<Object> update(@PathVariable(name = "c-id") Long cId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        CommentResponseDto commentResponseDto = commentService.update(cId, requestDto, userDetails.getUser());

//        try {
//            commentResponseDto = commentService.update(cId, requestDto, userDetails.getUser());
//        } catch (IllegalAccessException e) {
//            return new ResponseEntity<>(new ExcepMsg("작성자만 삭제/수정할 수 있습니다.", BAD_REQUEST.value()), BAD_REQUEST);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.", BAD_REQUEST.value()), BAD_REQUEST);
//        }
        return new ResponseEntity<>(commentResponseDto, OK);
    }

    @DeleteMapping("/board/{b-id}/comment/{c-id}")
    public ResponseEntity<Object> delete(@PathVariable(name = "c-id") Long cId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        ExcepMsg msg = commentService.deleteComment(cId, userDetails.getUser());
//        try {
//            msg = commentService.deleteComment(cId, userDetails.getUser());
//        } catch (IllegalAccessException e) {
//            return new ResponseEntity<>(new ExcepMsg("작성자만 삭제/수정할 수 있습니다.", BAD_REQUEST.value()), BAD_REQUEST);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.", BAD_REQUEST.value()), BAD_REQUEST);
//        }
        return new ResponseEntity<>(msg, OK);
    }

    @PostMapping("/board/{b-id}/comment/{c-id}/like")
    public ResponseEntity<Object> likeComment(@PathVariable(name = "c-id") Long cId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean isLike = commentLikeService.commentLike(cId, userDetails.getUser());

        if(isLike) {
            ExcepMsg msg = new ExcepMsg("좋아요", OK.value());
            return new ResponseEntity<>(msg, OK);
        }
        ExcepMsg msg = new ExcepMsg("좋아요 취소", OK.value());
        return new ResponseEntity<>(msg, OK);

    }
}
