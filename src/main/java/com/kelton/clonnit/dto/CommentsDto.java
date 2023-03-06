package com.kelton.clonnit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentsDto {

    private Long id;
    private Long postId;
    private Date createdDate = new Date();
    private String text;
    private String username;
}
