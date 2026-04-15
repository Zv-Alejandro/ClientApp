package org.ies.demo.fornix.clientapp.controllerSpring;

import org.ies.demo.fornix.clientapp.dto.tag.TagResponseDTO;
import org.ies.demo.fornix.clientapp.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("")
    public ResponseEntity<List<TagResponseDTO>> getAll() {
        try {
            return ResponseEntity.ok(tagService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDTO> getById(@PathVariable Integer id) {
        try {
            TagResponseDTO response = tagService.getById(id);
            if (response == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}