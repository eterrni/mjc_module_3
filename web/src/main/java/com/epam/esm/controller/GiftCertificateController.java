package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.certificate.GiftCertificateService;
import com.epam.esm.util.GiftCertificateQueryParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller that handles requests related to the gift certificate
 *
 * @author Alexander Novikov
 */
@RestController
@RequestMapping("/api")
public class GiftCertificateController extends HATEOASController<GiftCertificateDto> {

    private final GiftCertificateService service;

    @Autowired
    public GiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }

    /**
     * Invokes service method to get List of all GiftCertificates that matches parameters
     *
     * @param parameter is {@link GiftCertificateQueryParameter} object with requested parameters
     * @return List of {@link GiftCertificateDto} objects with GiftCertificate data.
     */
    @GetMapping("/gift-certificates")
    public PagedModel<GiftCertificateDto> readAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "4") int size,
            GiftCertificateQueryParameter parameter) {
        List<GiftCertificateDto> certificateDtoList = service.readAll(parameter, page, size);
        addLinksToListCertificate(certificateDtoList);
        return addPagination(certificateDtoList, page, size, service.getCountOfEntities(parameter));
    }

    /**
     * Get gift certificate by ID
     *
     * @param id of the gift certificate we want to get
     * @return the gift certificate on this ID
     */
    @GetMapping("/gift-certificate/{id}")
    public GiftCertificateDto read(@PathVariable int id) {
        return addLinksToCertificate(service.read(id));
    }

    /**
     * Create a new gift certificate
     *
     * @param giftCertificateDto we want to create
     * @return created gift certificate with its ID
     */
    @PostMapping("/gift-certificate")
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto create(@RequestBody GiftCertificateDto giftCertificateDto) {
        return service.create(giftCertificateDto);
    }

    /**
     * Update gift certificate
     *
     * @param giftCertificateDto modified
     */
    @PatchMapping("/gift-certificate/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);
        service.update(giftCertificateDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete gift certificate by ID
     *
     * @param id of the gift certificate we want to delete
     */
    @DeleteMapping("/gift-certificate/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
