package com.myblog.myblogdemo.controller;


import com.myblog.myblogdemo.dto.CommentRequestDto;
import com.myblog.myblogdemo.exception.ApiException;
import com.myblog.myblogdemo.model.Comment;
import com.myblog.myblogdemo.repository.CommentRepository;
import com.myblog.myblogdemo.security.UserDetailsImpl;
import com.myblog.myblogdemo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
public class CommentRestController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @GetMapping("/api/comment/{id}")
    public List<Comment> readComment(@PathVariable Long id){
        return commentRepository.findAllByPostId(id);

    }
    @PutMapping("/api/commentedit")
    public Long updateComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println(userDetails.getUser().getUsername());
        commentService.update(commentRequestDto, userDetails.getUser());
        return commentRequestDto.getId();
    }

    @DeleteMapping("/api/commentdelete/{id}")
    public String deleteComment(@PathVariable Long id, Model model){

        model.addAttribute("id", id);
        return "single";
    }

    @ExceptionHandler(value = {IllegalArgumentException.class })
    public ResponseEntity<Object> handleApiRequestException(IllegalArgumentException ex) {
        ApiException apiException = new ApiException(
                ex.getMessage(),
                // HTTP 400 -> Client Error
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(
                apiException,
                // HTTP 400 -> Client Error
                HttpStatus.BAD_REQUEST
        );
    }
}
