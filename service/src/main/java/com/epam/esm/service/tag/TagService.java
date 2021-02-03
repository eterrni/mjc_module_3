package com.epam.esm.service.tag;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NotExistIdEntityException;
import com.epam.esm.repository.exception.DuplicateNameException;
import com.epam.esm.repository.tag.TagRepository;
import com.epam.esm.service.ICRDService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TagService implements ICRDService<TagDto, Integer> {

    private TagRepository tagRepository;

    private ModelMapper modelMapper;

    @Autowired
    public TagService(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TagDto> readAll() {
        return tagRepository.readAll().stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto read(final Integer tagId) {
        Tag read = tagRepository.read(tagId);
        if (read == null) {
            throw new NotExistIdEntityException("There is no tag with ID = " + tagId + " in Database");
        } else return modelMapper.map(read, TagDto.class);
    }

    @Override
    public TagDto create(TagDto tagDto) {
        if (tagRepository.readTagByName(tagDto.getName()).isPresent()) {
            throw new DuplicateNameException("A tag with name = " + tagDto.getName() + " already exists");
        } else {
            Tag addedTag = tagRepository.create(modelMapper.map(tagDto, Tag.class));
            return modelMapper.map(addedTag, TagDto.class);
        }
    }


    @Override
    public void delete(final Integer tagId) {
        Tag read = tagRepository.read(tagId);
        if (read == null) {
            throw new NotExistIdEntityException("There is no tag with ID = " + tagId + " in Database");
        } else tagRepository.delete(tagId);
    }

}
