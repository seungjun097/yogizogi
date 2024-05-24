package com.green.yogizogi.service;

import com.green.yogizogi.dto.OrderDTO;
import com.green.yogizogi.dto.OrderListDTO;
import com.green.yogizogi.entity.Order;

import java.util.List;

public interface OrderService {
    void orderCreate(OrderDTO orderDTO, String memberEmail);
    List<OrderListDTO> orderList(String memberEmail);

    String findMerchantId(Long order_id);
}
