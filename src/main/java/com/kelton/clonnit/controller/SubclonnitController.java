package com.kelton.clonnit.controller;

import com.kelton.clonnit.dto.SubclonnitDto;
import com.kelton.clonnit.service.SubclonnitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subclonnit")
public class SubclonnitController {

    private final SubclonnitService subclonnitService;

    public SubclonnitController(SubclonnitService subclonnitService) {
        this.subclonnitService = subclonnitService;
    }

    @GetMapping
    public ResponseEntity<List<SubclonnitDto>> getAllSubclonnits() {
        return ResponseEntity.status(HttpStatus.OK).body(subclonnitService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubclonnitDto> getSubclonnit(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(subclonnitService.getSubclonnit(id));
    }

    @PostMapping
    public ResponseEntity<SubclonnitDto> createSubClonnit(@RequestBody SubclonnitDto subclonnitDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subclonnitService.save(subclonnitDto));
    }
}