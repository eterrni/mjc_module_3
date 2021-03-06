package com.epam.esm.service.tag;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateNameException;
import com.epam.esm.exception.NotExistEntityException;
import com.epam.esm.repository.tag.TagRepository;
import com.epam.esm.service.ITagService;
import com.epam.esm.util.Page;
import com.epam.esm.util.TagValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TagService implements ITagService {

    private TagRepository tagRepository;
    private ModelMapper modelMapper;

    @Autowired
    public TagService(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TagDto> readAll(int page, int size) {
        Page tagPage = new Page(page, size, tagRepository.getCountOfEntities());
        return tagRepository.readAll(tagPage.getOffset(), tagPage.getLimit()).stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto read(final Integer tagId) {
        Tag readedTag = tagRepository.read(tagId);
        if (readedTag == null) {
            throw new NotExistEntityException("There is no tag with ID = " + tagId + " in Database");
        } else {
            return modelMapper.map(readedTag, TagDto.class);
        }
    }

    @Override
    public TagDto create(TagDto tagDto) {
        TagValidator.validateForCreate(tagDto);
        if (tagRepository.readTagByName(tagDto.getName()).isPresent()) {
            throw new DuplicateNameException("A tag with name = " + tagDto.getName() + " already exists");
        } else {
            Tag addedTag = tagRepository.create(modelMapper.map(tagDto, Tag.class));
            return modelMapper.map(addedTag, TagDto.class);
        }
    }

    @Override
    public void delete(final Integer tagId) {
        Tag readedTag = tagRepository.read(tagId);
        if (readedTag == null) {
            throw new NotExistEntityException("There is no tag with ID = " + tagId + " in Database");
        } else {
            tagRepository.delete(tagId);
        }
    }

    @Override
    public long getCountOfEntities() {
        return tagRepository.getCountOfEntities();
    }

    @Override
    public TagDto getMostWidelyUsedTagFromUserWithHighestCostOfAllOrders() {
        return modelMapper.map(tagRepository.getMostWidelyUsedTagFromUser(), TagDto.class);
    }

}
