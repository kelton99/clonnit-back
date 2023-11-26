package com.kelton.clonnit.dto;

import com.kelton.clonnit.model.Subclonnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubclonnitRequest {

    private String name;
    private String description;

    public static Subclonnit mapToSubclonnit(final SubclonnitRequest subclonnitRequest) {

        return Subclonnit.builder()
                .name(subclonnitRequest.getName())
                .description(subclonnitRequest.getDescription())
                .createdDate(new Date())
                .numberOfPosts(0)
                .build();
    }
}
