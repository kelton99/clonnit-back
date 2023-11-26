package com.kelton.clonnit.controller;

import com.kelton.clonnit.dto.SubclonnitRequest;
import com.kelton.clonnit.dto.SubclonnitResponse;
import com.kelton.clonnit.service.SubclonnitService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subclonnit")
@AllArgsConstructor
public class SubclonnitController {

    private final SubclonnitService subclonnitService;

    @GetMapping
    public ResponseEntity<List<SubclonnitResponse>> getAllSubclonnits() {
        return ResponseEntity.status(HttpStatus.OK).body(subclonnitService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubclonnitResponse> getSubclonnit(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(subclonnitService.getSubclonnit(id));
    }

    @PostMapping
    public ResponseEntity<SubclonnitResponse> createSubClonnit(@RequestBody SubclonnitRequest subclonnitRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subclonnitService.save(subclonnitRequest));
    }
}