package com.kelton.clonnit.service;

import com.kelton.clonnit.dto.SubclonnitRequest;
import com.kelton.clonnit.dto.SubclonnitResponse;
import com.kelton.clonnit.exception.ClonnitException;
import com.kelton.clonnit.repository.SubclonnitRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubclonnitService {

    private final SubclonnitRepository subclonnitRepository;


    public SubclonnitResponse save(SubclonnitRequest subclonnitRequest) {
        final var subclonnitEntity = subclonnitRepository.save(SubclonnitRequest.mapToSubclonnit(subclonnitRequest));
        return new SubclonnitResponse(subclonnitEntity);
    }

    @Transactional
    public List<SubclonnitResponse> getAll() {
        return subclonnitRepository.findAll().stream().map(SubclonnitResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public SubclonnitResponse getSubclonnit(Long id) {
        final var subclonnit = subclonnitRepository.findById(id)
                .orElseThrow(() -> new ClonnitException("No subclonnit found with that id"));
        return new SubclonnitResponse(subclonnit);
    }


    public void increasePostCount(Long subclonnitId) {
        final var subclonnit = SubclonnitResponse.mapToSubclonnit(this.getSubclonnit(subclonnitId));
        subclonnit.setNumberOfPosts(subclonnit.getNumberOfPosts() + 1);
        this.subclonnitRepository.save(subclonnit);
    }

    public void decreasePostCount(Long subclonnitId) {
        final var subclonnit = SubclonnitResponse.mapToSubclonnit(this.getSubclonnit(subclonnitId));
        subclonnit.setNumberOfPosts(subclonnit.getNumberOfPosts() - 1);
        this.subclonnitRepository.save(subclonnit);
    }
}
