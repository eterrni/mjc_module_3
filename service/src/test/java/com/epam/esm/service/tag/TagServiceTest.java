package com.epam.esm.service.tag;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateNameException;
import com.epam.esm.exception.InvalidDataExeception;
import com.epam.esm.exception.NotExistIdEntityException;
import com.epam.esm.repository.tag.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ModelMapper modelMapper;

    private Tag tag;
    private TagDto tagDto;
    private List<Tag> tagList = new ArrayList<>();
    private List<TagDto> tagDtoList = new ArrayList<>();


    @BeforeEach
    void setUp() {
        tag = new Tag();
        tag.setId(1);
        tag.setName("tag");

        tagDto = new TagDto();
        tagDto.setId(1);
        tagDto.setName("user");

        tagList.add(tag);
        tagDtoList.add(tagDto);
    }

    @Test
    @DisplayName("Getting the list of TagDto")
    void readAll_returnsTheExpectedResult_test() {
        //when
        when(tagRepository.getCountOfEntities()).thenReturn(1L);
        when(tagRepository.readAll(0, 4)).thenReturn(tagList);
        when(modelMapper.map(tag, TagDto.class)).thenReturn(tagDto);
        //then
        assertEquals(tagDtoList, tagService.readAll(1, 4));
    }

    @Test
    @DisplayName("The method should return the TagDto")
    void read_returnsTheExpectedResult_test() {
        //when
        when(tagRepository.read(1)).thenReturn(tag);
        when(modelMapper.map(tag, TagDto.class)).thenReturn(tagDto);
        //then
        assertEquals(tagDto, tagService.read(1));
    }

    @Test
    @DisplayName("A Tag with such an ID does not exist, an appropriate exception must be thrown")
    void read_notExistId_thrownNotExistIdEntityException_test() {
        //when
        when(tagRepository.read(2)).thenReturn(null);
        //then
        assertThrows(NotExistIdEntityException.class, () -> tagService.read(2));
    }

    @Test
    @DisplayName("When creating a tag with invalid data, an exception is thrown InvalidDataException")
    void create_invalidNullName_thrownInvalidDataException_test() {
        assertThrows(InvalidDataExeception.class, () -> tagService.create(new TagDto()));
    }

    @Test
    @DisplayName("When trying to create a tag with a name that already exists, an exception is thrown DuplicateNameException")
    void create_duplicateTagName_thrownDuplicateNameException_test() {
        //when
        when(tagRepository.readTagByName(tagDto.getName())).thenReturn(Optional.of(tag));
        //then
        assertThrows(DuplicateNameException.class, () -> tagService.create(tagDto));
    }

    @Test
    @DisplayName("The successful establishment of the tag, the method should return TagDto with id")
    void create_returnsTheExpectedResult_test() {
        //given
        TagDto tagDto = new TagDto();
        tagDto.setName("newTagName");

        TagDto createdTagDto = new TagDto();
        createdTagDto.setId(2);
        createdTagDto.setName("newTagName");

        Tag tag = new Tag();
        tag.setName("newTagName");

        Tag createdTag = new Tag();
        createdTag.setId(2);
        createdTag.setName("newTagName");
        //when
        when(tagRepository.readTagByName(tagDto.getName())).thenReturn(Optional.empty());
        when(modelMapper.map(tagDto, Tag.class)).thenReturn(tag);
        when(tagRepository.create(tag)).thenReturn(createdTag);
        when(modelMapper.map(createdTag, TagDto.class)).thenReturn(createdTagDto);
        //then
        assertEquals(createdTagDto, tagService.create(tagDto));
    }

    @Test
    @DisplayName("If you try to delete a tag with a non-existent ID, will be thrown NotExistIdEntityException")
    void delete_notExistId_thrownNotExistIdEntityException_test() {
        //when
        when(tagRepository.read(123)).thenReturn(null);
        //then
        assertThrows(NotExistIdEntityException.class, () -> tagService.delete(123));
    }

    @Test
    @DisplayName("Successful remove tag, not thrown away no exception")
    void delete_theEntityWasRemovedFromTheDatabase_test() {
        //when
        when(tagRepository.read(1)).thenReturn(tag);
        //then
        assertDoesNotThrow(() -> tagService.delete(1));
    }

    @Test
    @DisplayName("Getting the number of existing tags")
    void getCountOfEntities_returnsTheExpectedResult_test() {
        long count = 2L;
        //when
        when(tagRepository.getCountOfEntities()).thenReturn(count);
        //then
        assertEquals(count, tagService.getCountOfEntities());
    }

}