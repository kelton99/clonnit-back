package com.kelton.clonnit.service;

import com.kelton.clonnit.dto.SubclonnitDto;
import com.kelton.clonnit.exception.ClonnitException;
import com.kelton.clonnit.model.Subclonnit;
import com.kelton.clonnit.repository.PostRepository;
import com.kelton.clonnit.repository.SubclonnitRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubclonnitService {

    private final SubclonnitRepository subclonnitRepository;
    private final PostRepository postRepository;

    public SubclonnitService(SubclonnitRepository subclonnitRepository, PostRepository postRepository) {
        this.subclonnitRepository = subclonnitRepository;
        this.postRepository = postRepository;
    }

    public SubclonnitDto save(SubclonnitDto subclonnitDto) {
        Subclonnit saved = mapSubclonnitDto(subclonnitDto);
        saved = subclonnitRepository.save(saved);
        subclonnitDto.setId(saved.getId());
        return subclonnitDto;
    }

    @Transactional
    public List<SubclonnitDto> getAll() {
        return subclonnitRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public SubclonnitDto getSubclonnit(Long id) {
        final Subclonnit subclonnit = subclonnitRepository.findById(id)
                .orElseThrow(() -> new ClonnitException("No subclonnit found with that id"));
        return this.mapToDto(subclonnit);
    }

    private SubclonnitDto mapToDto(Subclonnit subclonnit) {
        final SubclonnitDto subclonnitDto = new SubclonnitDto();
        final Integer numberOfPost = postRepository.findAllBySubclonnit(subclonnit).size();
        subclonnitDto.setId(subclonnit.getId());
        subclonnitDto.setName(subclonnit.getName());
        subclonnitDto.setNumberOfPosts(numberOfPost);
        subclonnitDto.setDescription(subclonnit.getDescription());
        return subclonnitDto;
    }

    private Subclonnit mapSubclonnitDto(SubclonnitDto subclonnitDto) {
        final Subclonnit subclonnit = new Subclonnit();
        subclonnit.setName(subclonnitDto.getName());
        subclonnit.setDescription(subclonnitDto.getDescription());
        return subclonnit;
    }


}