package com.kelton.clonnit.dto;

import com.kelton.clonnit.model.Subclonnit;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SubclonnitResponse {

    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
    private Date createdDate;

    public SubclonnitResponse(final Subclonnit subclonnit) {
        this.id = subclonnit.getId();
        this.name = subclonnit.getName();
        this.description = subclonnit.getDescription();
        this.numberOfPosts = subclonnit.getNumberOfPosts();
        this.createdDate = subclonnit.getCreatedDate();
    }

    public static Subclonnit mapToSubclonnit(final SubclonnitResponse subclonnitResponse) {
        return Subclonnit.builder()
                .id(subclonnitResponse.getId())
                .name(subclonnitResponse.getName())
                .description(subclonnitResponse.getDescription())
                .createdDate(subclonnitResponse.getCreatedDate())
                .numberOfPosts(subclonnitResponse.getNumberOfPosts())
                .build();
    }
}
