package com.epam.esm.service.certificate;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.InvalidDataExeception;
import com.epam.esm.exception.NotExistIdEntityException;
import com.epam.esm.repository.certificate.GiftCertificateRepository;
import com.epam.esm.repository.tag.TagRepository;
import com.epam.esm.service.IGiftCertificateService;
import com.epam.esm.util.GiftCertificateQueryParameter;
import com.epam.esm.util.GiftCertificateValidator;
import com.epam.esm.util.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class GiftCertificateService implements IGiftCertificateService {

    private GiftCertificateRepository giftCertificateRepository;
    private TagRepository tagDAO;
    private ModelMapper modelMapper;

    @Autowired
    public GiftCertificateService(GiftCertificateRepository giftCertificateRepository, TagRepository tagDAO, ModelMapper modelMapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagDAO = tagDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<GiftCertificateDto> readAll(int page, int size) {
        Page certificatePage = new Page(page, size, giftCertificateRepository.getCountOfEntities());
        return giftCertificateRepository.readAll(certificatePage.getOffset(), certificatePage.getLimit()).stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> readAll(GiftCertificateQueryParameter parameter, int page, int size) {
        if (parameter.isEmpty()) {
            return readAll(page, size);
        }
        Page certificatePage = new Page(page, size, giftCertificateRepository.getCountOfEntities(parameter));
        List<GiftCertificate> giftCertificates = giftCertificateRepository.readAll(parameter, certificatePage.getOffset(), certificatePage.getLimit());
        return giftCertificates.stream().map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class)).collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto read(final Integer id) {
        GiftCertificate readedGiftCertificate = giftCertificateRepository.read(id);
        if (readedGiftCertificate == null) {
            throw new NotExistIdEntityException("There is no gift certificate with ID = " + id + " in Database");
        } else {
            return modelMapper.map(readedGiftCertificate, GiftCertificateDto.class);
        }
    }

    @Override
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        if (!GiftCertificateValidator.validateForCreate(giftCertificateDto)) {
            throw new InvalidDataExeception("Invalid data for creating a certificate");
        }

        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        createAndSetTags(giftCertificate);
        giftCertificateRepository.create(giftCertificate);
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto modifiedGiftCertificateDto) {

        if (!GiftCertificateValidator.validateForUpdate(modifiedGiftCertificateDto)) {
            throw new InvalidDataExeception("Invalid data for update a certificate");
        }

        GiftCertificate readGiftCertificate = giftCertificateRepository.read(modifiedGiftCertificateDto.getId());
        if (readGiftCertificate == null) {
            throw new NotExistIdEntityException("There is no gift certificate with ID = " + modifiedGiftCertificateDto.getId() + " in Database");
        }

        GiftCertificate modifiedGiftCertificate = modelMapper.map(modifiedGiftCertificateDto, GiftCertificate.class);
        updateGiftCertificateFields(readGiftCertificate, modifiedGiftCertificate);
        GiftCertificate update = giftCertificateRepository.update(readGiftCertificate);
        return modelMapper.map(update, GiftCertificateDto.class);

    }

    @Override
    public void delete(final Integer id) {
        GiftCertificate readedGiftCertificate = giftCertificateRepository.read(id);
        if (readedGiftCertificate == null) {
            throw new NotExistIdEntityException("There is no gift certificate with ID = " + id + " in Database");
        } else {
            giftCertificateRepository.delete(id);
        }
    }

    @Override
    public long getCountOfEntities(GiftCertificateQueryParameter parameter) {
        return giftCertificateRepository.getCountOfEntities(parameter);
    }

    private void createAndSetTags(GiftCertificate giftCertificate) {
        List<Tag> tags = giftCertificate.getTags();
        if (tags != null) {
            List<Tag> prepared = new ArrayList<>();
            giftCertificate.setTags(prepared);

            tags.forEach(tag -> {
                Optional<Tag> readTagByName = tagDAO.readTagByName(tag.getName());
                if (!readTagByName.isPresent()) {
                    prepared.add(tag);
                } else {
                    Tag read = tagDAO.readTagByName(tag.getName()).get();
                    prepared.add(read);
                }
            });
        }
    }

    private void updateGiftCertificateFields(GiftCertificate readGiftCertificate, GiftCertificate modifiedGiftCertificate) {
        if (Objects.nonNull(modifiedGiftCertificate.getName())) {
            readGiftCertificate.setName(modifiedGiftCertificate.getName());
        }
        if (Objects.nonNull(modifiedGiftCertificate.getDescription())) {
            readGiftCertificate.setDescription(modifiedGiftCertificate.getDescription());
        }
        if (Objects.nonNull(modifiedGiftCertificate.getPrice())) {
            readGiftCertificate.setPrice(modifiedGiftCertificate.getPrice());
        }
        if (Objects.nonNull((modifiedGiftCertificate.getDuration()))) {
            readGiftCertificate.setDuration(modifiedGiftCertificate.getDuration());
        }
        if (Objects.nonNull(modifiedGiftCertificate.getTags())) {
            createAndSetTags(modifiedGiftCertificate);
            readGiftCertificate.setTags(modifiedGiftCertificate.getTags());
        }
    }
}