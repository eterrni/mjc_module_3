package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.tag.TagService;
import com.epam.esm.util.HATEOASBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller that handles requests related to the tag
 *
 * @author Alexander Novikov
 */
@RestController
@RequestMapping("/api")
public class TagController {

    private final TagService service;

    @Autowired
    public TagController(TagService service) {
        this.service = service;
    }

    /**
     * Get all tags
     *
     * @return List of found tags
     */
    @GetMapping("/tags")
    public List<TagDto> readAll() {
        return HATEOASBuilder.addLinksToTagDto(service.readAll());
    }

    /**
     * Get tag by id
     *
     * @param id of the tag we want to get
     * @return the tag on this id
     */
    @GetMapping("/tag/{id}")
    public TagDto read(@PathVariable int id) {
        return HATEOASBuilder.addLinksToTagDto(service.read(id));
    }

    /**
     * Create a new tag
     *
     * @param tagDto we want to create
     * @return created tag with its id
     */
    @PostMapping("/tag")
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody TagDto tagDto) {
        return service.create(tagDto);
    }

    /**
     * Delete a tag by ID
     *
     * @param id of the tag we want to delete
     */
    @DeleteMapping("/tag/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
