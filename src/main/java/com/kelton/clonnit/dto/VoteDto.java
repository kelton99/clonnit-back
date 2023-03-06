package com.kelton.clonnit.dto;

import com.kelton.clonnit.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VoteDto {

    private VoteType voteType;
    private Long postId;
}