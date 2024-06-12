package com.green.yogizogi.service;

import com.green.yogizogi.dto.OrderDTO;
import com.green.yogizogi.dto.OrderListDTO;
import com.green.yogizogi.dto.OrderMenuDTO;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.entity.Order;
import com.green.yogizogi.entity.OrderMenu;
import com.green.yogizogi.entity.Store;
import com.green.yogizogi.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final StoreRepository storeRepository;
    @Override
    public void orderCreate(OrderDTO orderDTO, String memberEmail) {
        Order order = Order.builder()
                .store(storeRepository.findById(orderDTO.getStore_id()).get())
                .address(addressRepository.findById(orderDTO.getAddress_id()).get())
                .member(memberRepository.findByEmail(memberEmail))
                .totalPrice(orderDTO.getTotalPrice())
                .merchant_uid(orderDTO.getMerchant_uid())
                .build();
        orderRepository.save(order);
        List<OrderMenuDTO> orderMenuDTOList = orderDTO.getOrderMenuDTOList();
        for (int i = 0; i < orderMenuDTOList.size(); i++) {
            OrderMenuDTO menuDTO = orderMenuDTOList.get(i);
            OrderMenu orderMenu = OrderMenu.builder()
                    .order(order)
                    .count(menuDTO.getCount())
                    .menuPrice(menuDTO.getMenuPrice())
                    .menuName(menuDTO.getMenuName())
                    .selectOp(menuDTO.getSelectOp())
                    .build();
            orderMenuRepository.save(orderMenu);
        }
    }
    @Override
    public List<OrderListDTO> orderList(String memberEmail) {
        Member member = memberRepository.findByEmail(memberEmail);
        List<Order> orderList = orderRepository.findByMember(member);
        List<OrderListDTO> orderListDTOList = new ArrayList<>();
        for (Order order : orderList) {
            OrderListDTO orderListDTO = OrderListDTO.builder()
                    .order_id(order.getId())
                    .regDate(order.getRegDate())
                    .deliveryTip(order.getStore().getDelivery_tip())
                    .storeName(order.getStore().getStore_name())
                    .store_id(order.getStore().getId())
                    .totalPrice(order.getTotalPrice())
                    .imgName(order.getStore().getImgName())
                    .path(order.getStore().getPath())
                    .uuid(order.getStore().getUuid())
                    .member_id(order.getMember().getId())
                    .nickname(order.getMember().getNickname())
                    .address1(order.getAddress().getAddress1())
                    .address2(order.getAddress().getAddress2())
                    .address3(order.getAddress().getAddress3())
                    .build();
            for(OrderMenu orderMenu : order.getOrderMenuList()) {
                OrderMenuDTO orderMenuDTO = OrderMenuDTO.builder()
                        .orderMenu_id(orderMenu.getId())
                        .count(orderMenu.getCount())
                        .menuName(orderMenu.getMenuName())
                        .menuPrice(orderMenu.getMenuPrice())
                        .selectOp(orderMenu.getSelectOp())
                        .build();
                orderListDTO.addMenuDTO(orderMenuDTO);
            }
            orderListDTOList.add(orderListDTO);
        }
        return orderListDTOList;
    }

    //스토어 아이디로 접수된 오더 조회
    @Override
    public List<OrderListDTO> mamagerOrderList(Long storeId) {
        Store store = storeRepository.findById(storeId).get();
        List<Order> orderList = orderRepository.findByStore(store);
        List<OrderListDTO> orderListDTOList = new ArrayList<>();
        for (Order order : orderList) {
            OrderListDTO orderListDTO = OrderListDTO.builder()
                    .order_id(order.getId())
                    .regDate(order.getRegDate())
                    .deliveryTip(order.getStore().getDelivery_tip())
                    .storeName(order.getStore().getStore_name())
                    .store_id(order.getStore().getId())
                    .totalPrice(order.getTotalPrice())
                    .imgName(order.getStore().getImgName())
                    .path(order.getStore().getPath())
                    .uuid(order.getStore().getUuid())
                    .member_id(order.getMember().getId())
                    .nickname(order.getMember().getNickname())
                    .address1(order.getAddress().getAddress1())
                    .address2(order.getAddress().getAddress2())
                    .address3(order.getAddress().getAddress3())
                    .build();
            for(OrderMenu orderMenu : order.getOrderMenuList()) {
                OrderMenuDTO orderMenuDTO = OrderMenuDTO.builder()
                        .orderMenu_id(orderMenu.getId())
                        .count(orderMenu.getCount())
                        .menuName(orderMenu.getMenuName())
                        .menuPrice(orderMenu.getMenuPrice())
                        .selectOp(orderMenu.getSelectOp())
                        .build();
                orderListDTO.addMenuDTO(orderMenuDTO);
            }
            orderListDTOList.add(orderListDTO);
        }
        return orderListDTOList;
    }

    @Override
    public String findMerchantId(Long order_id) {
        Order order = orderRepository.findById(order_id).get();
        return order.getMerchant_uid();
    }
}
