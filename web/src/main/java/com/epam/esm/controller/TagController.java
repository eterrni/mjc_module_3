package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
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
public class TagController extends HATEOASController<TagDto> {

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
    public PagedModel<TagDto> readAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "4") int size) {
        List<TagDto> tags = service.readAll(page, size);
        addLinksToListTag(tags);
        return addPagination(tags, page, size, service.getCountOfEntities());
    }

    /**
     * Get tag by id
     *
     * @param id of the tag we want to get
     * @return the tag on this id
     */
    @GetMapping("/tag/{id}")
    public TagDto read(@PathVariable int id) {
        return addLinksToTag(service.read(id));
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

    @GetMapping("/tag/mostUsedTag")
    public TagDto getMostWidelyUsedTagFromUserWithHighestCostOfAllOrders() {
        return service.getMostWidelyUsedTagFromUserWithHighestCostOfAllOrders();
    }

}
