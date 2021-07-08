package com.myblog.myblogdemo.service;


import com.myblog.myblogdemo.dto.CommentRequestDto;
import com.myblog.myblogdemo.model.Comment;
import com.myblog.myblogdemo.model.User;
import com.myblog.myblogdemo.repository.CommentRepository;
import com.myblog.myblogdemo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public void createComment(CommentRequestDto requestDto, Long postid, User user) {
        String comments = requestDto.getContents();
        Comment comment = new Comment(requestDto, postid, user);
        commentRepository.save(comment);
    }
}
