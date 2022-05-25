package com.kelton.clonnit.service;

import com.kelton.clonnit.dto.SubclonnitDto;
import com.kelton.clonnit.exception.ClonnitException;
import com.kelton.clonnit.model.Subclonnit;
import com.kelton.clonnit.repository.SubclonnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubclonnitService {

    private final SubclonnitRepository subclonnitRepository;

    public SubclonnitService(SubclonnitRepository subclonnitRepository) {
        this.subclonnitRepository = subclonnitRepository;
    }

    public SubclonnitDto save(SubclonnitDto subclonnitDto) {
        Subclonnit saved = mapSubclonnitDto(subclonnitDto);
        subclonnitDto.setId(subclonnitDto.getId());
        return subclonnitDto;
    }

    public List<SubclonnitDto> getAll() {
        return subclonnitRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public SubclonnitDto getSubclonnit(Long id) {
        final Subclonnit subclonnit = subclonnitRepository.findById(id)
                .orElseThrow(() -> new ClonnitException("No subclonnit found with that id"));
        return this.mapToDto(subclonnit);
    }

    private SubclonnitDto mapToDto(Subclonnit subclonnit) {
        final SubclonnitDto subclonnitDto = new SubclonnitDto();
        subclonnit.setId(subclonnitDto.getId());
        subclonnitDto.setName(subclonnit.getName());
        subclonnitDto.setNumberOfPosts(subclonnit.getPosts().size());
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