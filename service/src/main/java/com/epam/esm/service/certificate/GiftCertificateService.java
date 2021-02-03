package com.epam.esm.service.certificate;

import com.epam.esm.converter.SortAndOrderConverter;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameters;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NotExistIdEntityException;
import com.epam.esm.repository.certificate.GiftCertificateRepository;
import com.epam.esm.repository.tag.TagRepository;
import com.epam.esm.service.IGiftCertificateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class GiftCertificateService implements IGiftCertificateService {

    private static final String EMPTY_VALUE = "";
    private static final String PARAMETER_TAG_NAME = "tagName";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_DESCRIPTION = "description";
    private static final String PARAMETER_SORT_TYPE = "sortType";
    private static final String PARAMETER_ORDER_TYPE = "orderType";


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
    public List<GiftCertificateDto> readAll() {
        return giftCertificateRepository.readAll().stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto read(final Integer id) {
        GiftCertificate read = giftCertificateRepository.read(id);
        if (read == null) {
            throw new NotExistIdEntityException("There is no gift certificate with ID = " + id + " in Database");
        } else return modelMapper.map(read, GiftCertificateDto.class);
    }

    @Override
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        createAndSetTags(giftCertificate);
        giftCertificateRepository.create(giftCertificate);
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    @Override
    public List<GiftCertificateDto> readByQueryParameters(String tagName, String name, String description, String sortType, String orderType) {
        if (isAllQueryParametersNull(tagName, name, description, sortType, orderType)) {
            return readAll();
        } else {
            String orderTypeConverter = SortAndOrderConverter.orderTypeConverter(orderType);
            String sortTypeConverter = SortAndOrderConverter.sortTypeConverter(sortType);
            HashMap<String, String> parametersMap = new HashMap<>();
            parametersMap.put(PARAMETER_TAG_NAME, tagName);
            parametersMap.put(PARAMETER_NAME, name);
            parametersMap.put(PARAMETER_DESCRIPTION, description);
            parametersMap.put(PARAMETER_SORT_TYPE, sortTypeConverter);
            parametersMap.put(PARAMETER_ORDER_TYPE, orderTypeConverter);
            prepareParametersForRequest(parametersMap);
            return giftCertificateRepository.readByQueryParameters(parametersMap).stream().map(this::joinGiftCertificateAndTags).collect(Collectors.toList());
        }
    }


    @Override
    public GiftCertificateDto update(GiftCertificateDto modifiedGiftCertificateDto) {
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
        GiftCertificate read = giftCertificateRepository.read(id);
        if (read == null) {
            throw new NotExistIdEntityException("There is no gift certificate with ID = " + id + " in Database");
        } else giftCertificateRepository.delete(id);
    }

    private GiftCertificateDto joinGiftCertificateAndTags(GiftCertificate giftCertificate) {
        giftCertificateRepository.joinCertificatesAndTags(Collections.singletonList(giftCertificate));
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    private void prepareParametersForRequest(HashMap<String, String> parameters) {
        prepareParametersForRequest_TagName_Name_Description(parameters);
        prepareParametersForRequest_SortType_OrderType(parameters);
    }

    private void prepareParametersForRequest_SortType_OrderType(HashMap<String, String> parameters) {
        if (Objects.isNull(parameters.get(PARAMETER_SORT_TYPE))) {
            parameters.put(PARAMETER_SORT_TYPE, GiftCertificateQueryParameters.SortType.DEFAULT.getSortType());
            parameters.put(PARAMETER_ORDER_TYPE, GiftCertificateQueryParameters.OrderType.DEFAULT.getOrderType());
        } else {
            if (Objects.isNull(parameters.get(PARAMETER_ORDER_TYPE))) {
                parameters.put(PARAMETER_ORDER_TYPE, GiftCertificateQueryParameters.OrderType.DEFAULT.getOrderType());
            }
        }
    }

    private void prepareParametersForRequest_TagName_Name_Description(HashMap<String, String> parameters) {
        if (Objects.isNull(parameters.get(PARAMETER_TAG_NAME))) {
            parameters.put(PARAMETER_TAG_NAME, EMPTY_VALUE);
        }
        if (Objects.isNull(parameters.get(PARAMETER_NAME))) {
            parameters.put(PARAMETER_NAME, EMPTY_VALUE);
        }
        if (Objects.isNull(parameters.get(PARAMETER_DESCRIPTION))) {
            parameters.put(PARAMETER_DESCRIPTION, EMPTY_VALUE);
        }
    }

    private boolean isAllQueryParametersNull(String... parameters) {
        return Arrays.stream(parameters).allMatch(Objects::isNull);
    }

    private void createAndSetTags(GiftCertificate giftCertificate) {
        List<Tag> tags = giftCertificate.getTags();
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

    protected void updateGiftCertificateFields(GiftCertificate readGiftCertificate, GiftCertificate modifiedGiftCertificate) {
        if (Objects.nonNull((modifiedGiftCertificate.getDuration()))) {
            readGiftCertificate.setDuration(modifiedGiftCertificate.getDuration());
        }
        if (Objects.nonNull(modifiedGiftCertificate.getDescription())) {
            readGiftCertificate.setDescription(modifiedGiftCertificate.getDescription());
        }
        if (Objects.nonNull(modifiedGiftCertificate.getName())) {
            readGiftCertificate.setName(modifiedGiftCertificate.getName());
        }
        if (Objects.nonNull(modifiedGiftCertificate.getPrice())) {
            readGiftCertificate.setPrice(modifiedGiftCertificate.getPrice());
        }
        if (Objects.nonNull(modifiedGiftCertificate.getTags())) {
            createAndSetTags(modifiedGiftCertificate);
            readGiftCertificate.setTags(modifiedGiftCertificate.getTags());
        }
    }
}