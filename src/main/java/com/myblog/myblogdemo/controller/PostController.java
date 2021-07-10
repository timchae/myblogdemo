package com.myblog.myblogdemo.controller;

import com.myblog.myblogdemo.dto.PostRequestDto;
import com.myblog.myblogdemo.dto.PostResponceDto;
import com.myblog.myblogdemo.model.Post;
import com.myblog.myblogdemo.repository.PostRepository;
import com.myblog.myblogdemo.security.UserDetailsImpl;
import com.myblog.myblogdemo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    @PostMapping("/api/post")
    public String createPost(PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userId = userDetails.getUser().getId();
        String username = userDetails.getUser().getUsername();
        Post post = new Post(requestDto, userId, username);
        postRepository.save(post);
        return "redirect:/";
//                postRepository.save(post);
    }

    @GetMapping("/api/post")
    public List<Post> readPost(){
        return postRepository.findAllByOrderByModifiedAtDesc();
    }


    @PutMapping("/api/post/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto){
        postService.update(id,requestDto);
        return id;
    }

    @DeleteMapping("/api/post/{id}")
    public Long deletePost(@PathVariable Long id){
        postRepository.deleteById(id);
        return id;
    }

    @GetMapping("/api/post/{id}")
    public Optional<Post> readPost(@PathVariable Long id){
//        System.out.println(id);
        return postRepository.findById(id);
    }



}



