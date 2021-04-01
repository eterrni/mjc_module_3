package com.epam.esm.service.order;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NotExistEntityException;
import com.epam.esm.repository.certificate.GiftCertificateRepository;
import com.epam.esm.repository.order.OrderRepository;
import com.epam.esm.repository.user.UserRepository;
import com.epam.esm.util.CreateParameterOrder;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private ModelMapper modelMapper;

    private static final Integer EXISTING_ID = 1;
    private static final Integer NON_EXISTING_ID = 1111111111;
    private User user;
    private UserDto userDto;
    private Order order;
    private OrderDto orderDto;
    private List<Order> orderList;
    private List<OrderDto> orderDtoList;
    private LocalDateTime orderDate;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setId(EXISTING_ID);
        user.setName("user");

        userDto = new UserDto();
        userDto.setId(1);
        userDto.setName("user");

        orderDate = LocalDateTime.now(ZoneId.systemDefault());

        order = new Order();
        order.setId(EXISTING_ID);
        order.setPrice(new BigDecimal(1));
        order.setUser(user);
        order.setDate(orderDate);

        orderDto = new OrderDto();
        orderDto.setId(1);
        orderDto.setDate(orderDate);
        orderDto.setPrice(new BigDecimal(1));
        orderDto.setUser(userDto);

        orderList = new ArrayList<>();
        orderList.add(order);

        orderDtoList = new ArrayList<>();
        orderDtoList.add(orderDto);

    }

    @Test
    @DisplayName("Getting the list of OrderDto")
    void readAll_returnsTheExpectedResult_test() {
        //given
        long count = 1L;
        //when
        when(orderRepository.getCountOfEntities()).thenReturn(count);
        when(orderRepository.readAll(0, 4)).thenReturn(orderList);
        when(modelMapper.map(order, OrderDto.class)).thenReturn(orderDto);
        //then
        assertEquals(orderDtoList, orderService.readAll(1, 4));
    }

    @Test
    @DisplayName("The method should return the OrderDto")
    void read_returnsTheExpectedResult_test() {
        //when
        when(orderRepository.read(EXISTING_ID)).thenReturn(order);
        when(modelMapper.map(order, OrderDto.class)).thenReturn(orderDto);
        //then
        assertEquals(orderDto, orderService.read(EXISTING_ID));
    }

    @Test
    @DisplayName("A Order with such an ID does not exist, an appropriate exception must be thrown")
    void read_notExistId_thrownNotExistIdEntityException_test() {
        //when
        when(orderRepository.read(NON_EXISTING_ID)).thenReturn(null);
        //then
        assertThrows(NotExistEntityException.class, () -> orderService.read(NON_EXISTING_ID));
    }


    @Test
    @DisplayName("Getting the number of existing orders")
    void getCountOfEntities_returnsTheExpectedResult_test() {
        long count = 2L;
        //when
        when(orderRepository.getCountOfEntities()).thenReturn(count);
        //then
        assertEquals(count, orderRepository.getCountOfEntities());
    }
}