package com.myblog.myblogdemo.model;


import com.myblog.myblogdemo.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Long postId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public Comment(CommentRequestDto requestDto, Long postId, User user) {

        this.contents = requestDto.getContents();
        this.postId = postId;
        this.user = user;
    }

    public void update(String contents){
        this.contents = contents;
    }
}
