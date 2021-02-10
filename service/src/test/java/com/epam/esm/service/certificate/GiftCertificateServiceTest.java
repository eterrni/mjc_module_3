package com.epam.esm.service.certificate;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.InvalidDataExeception;
import com.epam.esm.exception.NotExistIdEntityException;
import com.epam.esm.repository.certificate.GiftCertificateRepository;
import com.epam.esm.repository.tag.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {

    @InjectMocks
    private GiftCertificateService giftCertificateService;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private TagRepository tagDAO;

    @Mock
    private ModelMapper modelMapper;
    private static final int EXISTING_ID = 1;
    private static final int NON_EXISTING_ID = 1111111;

    private LocalDateTime dateTime;

    private GiftCertificate giftCertificate;
    private List<GiftCertificate> giftCertificateList;
    private GiftCertificateDto giftCertificateDto;
    private List<GiftCertificateDto> giftCertificateDtoList;

    private Tag tag;
    private List<Tag> tagList;
    private TagDto tagDto;
    private List<TagDto> tagDtoList;

    @BeforeEach
    void setUp() {
        dateTime = LocalDateTime.now(ZoneId.systemDefault());

        tag = new Tag();
        tag.setId(EXISTING_ID);
        tag.setName("tag");

        tagList = new ArrayList<>();
        tagList.add(tag);

        tagDto = new TagDto();
        tagDto.setId(EXISTING_ID);
        tagDto.setName("tag");

        tagDtoList = new ArrayList<>();
        tagDtoList.add(tagDto);

        giftCertificate = new GiftCertificate();
        giftCertificate.setId(EXISTING_ID);
        giftCertificate.setName("certificate");
        giftCertificate.setPrice(new BigDecimal(1));
        giftCertificate.setDuration(1);
        giftCertificate.setDescription("description");
        giftCertificate.setCreateDate(dateTime);
        giftCertificate.setLastUpdateDate(dateTime);
        giftCertificate.setTags(tagList);

        giftCertificateList = new ArrayList<>();
        giftCertificateList.add(giftCertificate);

        giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(EXISTING_ID);
        giftCertificateDto.setName("certificate");
        giftCertificateDto.setPrice(new BigDecimal(1));
        giftCertificateDto.setDuration(1);
        giftCertificateDto.setDescription("description");
        giftCertificateDto.setCreateDate(dateTime);
        giftCertificateDto.setLastUpdateDate(dateTime);
        giftCertificateDto.setTags(tagDtoList);

        giftCertificateDtoList = new ArrayList<>();
        giftCertificateDtoList.add(giftCertificateDto);

    }

    @Test
    @DisplayName("Getting the list of CertificateDto")
    void readAll_returnsTheExpectedResult_test() {
        long count = 1L;
        //when
        when(giftCertificateRepository.getCountOfEntities()).thenReturn(count);
        when(giftCertificateRepository.readAll(0, 4)).thenReturn(giftCertificateList);
        when(modelMapper.map(giftCertificate, GiftCertificateDto.class)).thenReturn(giftCertificateDto);
        //then
        assertEquals(giftCertificateDtoList, giftCertificateService.readAll(1, 4));
    }

    @Test
    @DisplayName("The method should return the CertificateDto")
    void read_returnsTheExpectedResult_test() {
        //when
        when(giftCertificateRepository.read(EXISTING_ID)).thenReturn(giftCertificate);
        when(modelMapper.map(giftCertificate, GiftCertificateDto.class)).thenReturn(giftCertificateDto);
        //then
        assertEquals(giftCertificateDto, giftCertificateService.read(EXISTING_ID));
    }

    @Test
    @DisplayName("A Certificate with such an ID does not exist, an appropriate exception must be thrown")
    void read_notExistId_thrownNotExistIdEntityException_test() {
        //when
        when(giftCertificateRepository.read(NON_EXISTING_ID)).thenReturn(null);
        //then
        assertThrows(NotExistIdEntityException.class, () -> giftCertificateService.read(NON_EXISTING_ID));
    }

    @Test
    @DisplayName("When creating a certificate with invalid data, an exception is thrown InvalidDataException")
    void create_invalidData_thrownInvalidDataException_test() {
        //given
        GiftCertificateDto nonValidCertificateDto = new GiftCertificateDto();
        //then
        assertThrows(InvalidDataExeception.class, () -> giftCertificateService.create(nonValidCertificateDto));
    }

    @Test
    @DisplayName("Successful certificate creation, an order with a number is expected.")
    void create_successfulCertificateCreation() {
        //when
        when(modelMapper.map(giftCertificateDto, GiftCertificate.class)).thenReturn(giftCertificate);
        when(tagDAO.readTagByName(tag.getName())).thenReturn(Optional.of(tag));
        when(giftCertificateRepository.create(giftCertificate)).thenReturn(giftCertificate);
        when(modelMapper.map(giftCertificate, GiftCertificateDto.class)).thenReturn(giftCertificateDto);
        //then
        assertEquals(giftCertificateDto, giftCertificateService.create(giftCertificateDto));

    }

    @Test
    @DisplayName("Invalid update data, expected InvalidDataException")
    void update_invalidData_thrownInvalidDataException_test() {
        //given
        GiftCertificateDto invalidCertificateDto = new GiftCertificateDto();
        invalidCertificateDto.setDuration(-12);
        //then
        assertThrows(InvalidDataExeception.class, () -> giftCertificateService.update(invalidCertificateDto));
    }

    @Test
    @DisplayName("A Certificate with such an ID does not exist, an appropriate exception must be thrown")
    void update_nonExistId_thrownNonExistIdEntityException_test() {
        //given
        GiftCertificateDto nonExistCertificate = new GiftCertificateDto();
        nonExistCertificate.setId(NON_EXISTING_ID);
        //when
        when(giftCertificateRepository.read(nonExistCertificate.getId())).thenReturn(null);
        //then
        assertThrows(NotExistIdEntityException.class, () -> giftCertificateService.update(nonExistCertificate));
    }


    @Test
    @DisplayName("Successful certificate update")
    void update_successfulCertificateUpdate() {
        //when
        when(giftCertificateRepository.read(EXISTING_ID)).thenReturn(giftCertificate);
        when(modelMapper.map(giftCertificateDto, GiftCertificate.class)).thenReturn(giftCertificate);
        when(giftCertificateRepository.update(giftCertificate)).thenReturn(giftCertificate);
        when(modelMapper.map(giftCertificate, GiftCertificateDto.class)).thenReturn(giftCertificateDto);
        //then
        assertEquals(giftCertificateDto, giftCertificateService.update(giftCertificateDto));
    }

    @Test
    @DisplayName("If you try to delete a tag with a non-existent ID, will be thrown NotExistIdEntityException")
    void delete_notExistId_thrownNotExistIdEntityException_test() {
        //when
        when(giftCertificateRepository.read(NON_EXISTING_ID)).thenReturn(null);
        //then
        assertThrows(NotExistIdEntityException.class, () -> giftCertificateService.delete(NON_EXISTING_ID));
    }

    @Test
    @DisplayName("Successful remove tag, not thrown away no exception")
    void delete_theEntityWasRemovedFromTheDatabase_test() {
        //when
        when(giftCertificateRepository.read(EXISTING_ID)).thenReturn(giftCertificate);
        //then
        assertDoesNotThrow(() -> giftCertificateService.delete(EXISTING_ID));

    }
}