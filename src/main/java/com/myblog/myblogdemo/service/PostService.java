package com.myblog.myblogdemo.service;

import com.myblog.myblogdemo.dto.PostRequestDto;
import com.myblog.myblogdemo.model.Post;
import com.myblog.myblogdemo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long update(Long id, PostRequestDto requestDto){
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("NO ID")
        );
        post.update(requestDto);
        return id;
    }


}
