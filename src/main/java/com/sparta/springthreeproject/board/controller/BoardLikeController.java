package com.sparta.springthreeproject.board.controller;

import com.sparta.springthreeproject.board.dto.BoardLikeDto;
import com.sparta.springthreeproject.board.service.BoardLikeService;
import com.sparta.springthreeproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardLikeController {
    private final BoardLikeService boardLikeService;

    @PostMapping("/like/{id}")
    public BoardLikeDto pressLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardLikeService.boardLike(id, userDetails.getUser());
    }
}
