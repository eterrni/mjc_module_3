package com.epam.esm.service.order;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NotExistIdEntityException;
import com.epam.esm.repository.certificate.GiftCertificateRepository;
import com.epam.esm.repository.order.OrderRepository;
import com.epam.esm.repository.user.UserRepository;
import com.epam.esm.service.IOrderService;
import com.epam.esm.util.CreateParameterOrder;
import com.epam.esm.util.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final GiftCertificateRepository giftCertificateRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderService(UserRepository userRepository, OrderRepository orderRepository, GiftCertificateRepository giftCertificateRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.giftCertificateRepository = giftCertificateRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<OrderDto> readAll(int page, int size) {
        Page orderPage = new Page(page, size, orderRepository.getCountOfEntities());
        return orderRepository.readAll(orderPage.getOffset(), orderPage.getLimit()).stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto read(int id) {
        Order readedOrder = orderRepository.read(id);
        if (readedOrder == null) {
            throw new NotExistIdEntityException("There is no order with ID = " + id + " in Database");
        } else {
            return modelMapper.map(readedOrder, OrderDto.class);
        }
    }

    @Override
    public List<OrderDto> readOrdersByUserID(int userID) {
        return orderRepository.readOrdersByUserID(userID).stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDto create(CreateParameterOrder createParameterOrder) {

        int userId = createParameterOrder.getUserID();
        User user = userRepository.read(userId);
        if (user == null) {
            throw new NotExistIdEntityException("There is no user with ID =" + userId + " in Database");
        }

        List<GiftCertificate> giftCertificateList = new ArrayList<>();
        double price = 0;

        for (int giftId : createParameterOrder.getGiftsId()) {
            GiftCertificate read = giftCertificateRepository.read(giftId);
            if (read == null) {
                throw new NotExistIdEntityException("There is no gift certificate with ID =" + giftId + " in Database");
            }
            price += read.getPrice().doubleValue();
            giftCertificateList.add(read);
        }

        Order order = new Order();
        order.setGiftCertificateList(giftCertificateList);
        order.setPrice(new BigDecimal(price));
        order.setUser(user);

        return modelMapper.map(orderRepository.create(order), OrderDto.class);
    }

    public long getCountOfEntities() {
        return orderRepository.getCountOfEntities();
    }
}
