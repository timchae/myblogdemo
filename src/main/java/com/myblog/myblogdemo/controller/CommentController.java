package com.myblog.myblogdemo.controller;

import com.myblog.myblogdemo.dto.CommentRequestDto;
import com.myblog.myblogdemo.model.Post;
import com.myblog.myblogdemo.security.UserDetailsImpl;
import com.myblog.myblogdemo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/api/comment/{id}")
    public String  createPost(@PathVariable Long id, CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        System.out.println(requestDto);
        commentService.createComment(requestDto, id,  userDetails.getUser());
        model.addAttribute("id", id);
        return "single";
//                postRepository.save(post);
    }
}
