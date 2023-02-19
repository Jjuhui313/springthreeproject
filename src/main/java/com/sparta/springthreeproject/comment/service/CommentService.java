package com.sparta.springthreeproject.comment.service;

import com.sparta.springthreeproject.board.repository.BoardRepository;
import com.sparta.springthreeproject.comment.entity.Comment;
import com.sparta.springthreeproject.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    public List<Comment> getComment(Long id) {
        List<Comment> comments = commentRepository.findByBoard_IdOrderByCreateAtDesc(id);
        return comments.stream().filter(comment -> !comment.getIsDeleted()).collect(Collectors.toList());
    }
}
