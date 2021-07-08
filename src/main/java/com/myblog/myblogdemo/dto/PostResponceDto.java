package com.myblog.myblogdemo.dto;


import com.myblog.myblogdemo.model.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponceDto {
    private String name;
    private String title;
    private String contents;
    private String url;
    private Long id;
    private String username;
}
