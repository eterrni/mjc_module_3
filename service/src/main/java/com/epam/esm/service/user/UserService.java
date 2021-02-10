package com.epam.esm.service.user;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NotExistIdEntityException;
import com.epam.esm.repository.user.UserRepository;
import com.epam.esm.service.IUserService;
import com.epam.esm.util.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public UserDto read(int id) {
        User read = userRepository.read(id);
        if (read == null) {
            throw new NotExistIdEntityException("There is no user with ID = " + id + " in Database");
        } else return modelMapper.map(read, UserDto.class);
    }

    @Override
    public List<UserDto> readAll(int page, int size) {
        Page userPage = new Page(page, size, userRepository.getCountOfEntities());
        return userRepository.readAll(userPage.getOffset(), userPage.getLimit()).stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public long getCountOfEntities() {
        return userRepository.getCountOfEntities();
    }
}
