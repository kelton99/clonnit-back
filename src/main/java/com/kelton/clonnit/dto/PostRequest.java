package com.kelton.clonnit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostRequest {

    private Long subclonnitId;
    private String postName;
    private String description;
}
