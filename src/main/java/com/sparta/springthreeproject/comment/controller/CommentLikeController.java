package com.sparta.springthreeproject.comment.controller;

import com.sparta.springthreeproject.comment.dto.CommentLikeDto;
import com.sparta.springthreeproject.comment.service.CommentLikeService;
import com.sparta.springthreeproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("comment/{c-id}/like")
    public CommentLikeDto pressCommentLike(@PathVariable(name = "c-id") Long cId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentLikeService.commentLike(cId, userDetails.getUser());
    }
}
