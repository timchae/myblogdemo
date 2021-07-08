package com.myblog.myblogdemo.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostRequestDto {

    private String name;
    private String title;
    private String contents;
    private String url;
}
