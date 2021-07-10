package com.myblog.myblogdemo.service;


import com.myblog.myblogdemo.dto.CommentRequestDto;
import com.myblog.myblogdemo.model.Comment;
import com.myblog.myblogdemo.model.User;
import com.myblog.myblogdemo.repository.CommentRepository;
import com.myblog.myblogdemo.security.UserDetailsImpl;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment createComment(CommentRequestDto requestDto, Long postid, User user) {
        Comment comment = new Comment(requestDto, postid, user);
        comment = commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public Long update(CommentRequestDto requestDto, User user){
        Long id = requestDto.getId();
        String contents = requestDto.getContents();
        System.out.println("helllooooooooooooooooo"+contents);
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("NOid")
        );

        User user1 = comment.getUser();

        if(!user1.getUsername().equals(user.getUsername())){
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        comment.update(contents);
        return id;
    }
}
