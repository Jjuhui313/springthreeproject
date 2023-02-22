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

    @PostMapping("board/{b-id}/like")
    public BoardLikeDto pressBoardLike(@PathVariable(name = "b-id") Long bId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardLikeService.boardLike(bId, userDetails.getUser());
    }
}
