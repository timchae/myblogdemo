package com.myblog.myblogdemo.model;

import com.myblog.myblogdemo.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped{


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;


    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    private String url;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String username;

    public Post(PostRequestDto requestDto, Long userId, String username ){

        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.url = requestDto.getUrl();
        this.userId = userId;
        this.username = username;
    }

    public void update(PostRequestDto requestDto){

        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
        this.url = requestDto.getUrl();

    }
    /*필요 기능
    1. 제목, 작성자명, 작성 날자 조회
    2. 작성날짜 내림차순
    3.
     */
}
