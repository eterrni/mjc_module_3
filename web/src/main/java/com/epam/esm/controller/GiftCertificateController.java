package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.certificate.GiftCertificateService;
import com.epam.esm.util.GiftCertificateQueryParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller that handles requests related to the gift certificate
 *
 * @author Alexander Novikov
 */
@RestController
@RequestMapping("/api")
public class GiftCertificateController {

    @Autowired
    private GiftCertificateService service;

    /**
     * Invokes service method to get List of all GiftCertificates that matches parameters
     *
     * @param parameter is {@link GiftCertificateQueryParameter} object with requested parameters
     * @return List of {@link GiftCertificateDto} objects with GiftCertificate data.
     */
    @GetMapping("/gift-certificates")
    public List<GiftCertificateDto> readAll(GiftCertificateQueryParameter parameter) {
        return service.readAll(parameter);
    }

    /**
     * Get gift certificate by ID
     *
     * @param id of the gift certificate we want to get
     * @return the gift certificate on this ID
     */
    @GetMapping("/gift-certificate/{id}")
    public GiftCertificateDto read(@PathVariable int id) {
        return service.read(id);
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
    @PutMapping("/gift-certificate/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);
        service.update(giftCertificateDto);
    }

    /**
     * Delete gift certificate by ID
     *
     * @param id of the gift certificate we want to delete
     */
    @DeleteMapping("/gift-certificate/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

}
