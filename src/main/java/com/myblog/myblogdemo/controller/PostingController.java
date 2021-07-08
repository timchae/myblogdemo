package com.myblog.myblogdemo.controller;

import com.myblog.myblogdemo.model.Post;
import com.myblog.myblogdemo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class PostingController {


    private final PostRepository postRepository;

    @GetMapping("/post/one/{id}")
    public String  readOnePost(Model model, @PathVariable Long id){
        model.addAttribute("id", id);
        return "single";
    }

}
