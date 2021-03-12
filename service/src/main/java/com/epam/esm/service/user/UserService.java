package com.epam.esm.service.user;

import com.epam.esm.dto.RegistrationUserDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NotExistEntityException;
import com.epam.esm.exception.UserAlreadyExistException;
import com.epam.esm.repository.user.UserRepository;
import com.epam.esm.security.JwtUser;
import com.epam.esm.service.IUserService;
import com.epam.esm.util.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<UserDto> readAll(int page, int size) {
        Page userPage = new Page(page, size, userRepository.getCountOfEntities());
        return userRepository.readAll(userPage.getOffset(), userPage.getLimit()).stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto read(int id) {
        User readedUser = userRepository.read(id);
        if (readedUser == null) {
            throw new NotExistEntityException("There is no user with ID = " + id + " in Database");
        } else {
            return modelMapper.map(readedUser, UserDto.class);
        }
    }

    @Override
    public UserDto create(RegistrationUserDto registrationUserDto) {
        User user = userRepository.readByEmail(registrationUserDto.getEmail());

        if (user != null) {
            throw new UserAlreadyExistException("A user with email = " + registrationUserDto.getEmail() + " already exists");
        }
        User newUser = new User();
        newUser.setName(registrationUserDto.getName());
        newUser.setEmail(registrationUserDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        newUser.setRole(Role.ROLE_USER);

        userRepository.create(newUser);
        return modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public void delete(int id) {
        User user = userRepository.read(id);
        if (user == null) {
            throw new NotExistEntityException("There is no user with ID = " + id + " in Database");
        } else {
            userRepository.delete(id);
        }
    }

    @Override
    @Transactional
    public UserDto changeRole(int id) {
        User user = userRepository.read(id);
        if (user == null) {
            throw new NotExistEntityException("There is no user with ID = " + id + " in Database");
        } else {
            if (user.getRole() == Role.ROLE_ADMIN) {
                user.setRole(Role.ROLE_USER);
            } else {
                user.setRole(Role.ROLE_ADMIN);
            }
            return modelMapper.map(user, UserDto.class);
        }
    }

    @Override
    public Role getUserRole(int id) {
        User user = userRepository.read(id);
        if (user == null) {
            throw new NotExistEntityException("There is no user with ID = " + id + " in Database");
        } else {
            return user.getRole();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.readByEmail(email);
        if (user == null) {
            throw new NotExistEntityException("There is no user with email = " + email + " in Database");
        } else {
            return new JwtUser(
                    user.getId(),
                    user.getEmail(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.getRole())
            );
        }
    }

    public long getCountOfEntities() {
        return userRepository.getCountOfEntities();
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role userRole) {
        return Collections.singletonList(new SimpleGrantedAuthority(userRole.name()));
    }
}
