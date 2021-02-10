package com.epam.esm.service.user;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NotExistIdEntityException;
import com.epam.esm.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private User user;
    private UserDto userDto;
    private List<User> userList;
    private List<UserDto> userDtoList;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1);
        user.setName("user");

        userDto = new UserDto();
        userDto.setId(1);
        userDto.setName("user");
        userList = new ArrayList<>();
        userDtoList = new ArrayList<>();
        userList.add(user);
        userDtoList.add(userDto);
    }

    @Test
    @DisplayName("The method should return the user")
    public void read_returnsTheExpectedResult_test() {
        //when
        when(userRepository.read(1)).thenReturn(user);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);
        //then
        Assertions.assertEquals(userDto, userService.read(1));
    }

    @Test
    @DisplayName("A user with such an ID does not exist, an appropriate exception must be thrown")
    public void read_notExistId_thrownNotExistIdEntityException_test() {
        //when
        when(userRepository.read(2)).thenReturn(null);
        //then
        assertThrows(NotExistIdEntityException.class, () -> userService.read(2));
    }

    @Test
    @DisplayName("Getting a list of UsersDto.")
    public void readAll_returnsTheExpectedResult_test() {
        //when
        when(userRepository.getCountOfEntities()).thenReturn(1L);
        when(userRepository.readAll(0, 4)).thenReturn(userList);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);
        //then
        assertEquals(userDtoList, userService.readAll(1, 4));
    }

    @Test
    @DisplayName("Getting a list of users.")
    public void getCountOfEntities_returnsTheExpectedResult_test() {
        //when
        when(userRepository.getCountOfEntities()).thenReturn(1L);
        //then
        assertEquals(1L, userService.getCountOfEntities());
    }
}
