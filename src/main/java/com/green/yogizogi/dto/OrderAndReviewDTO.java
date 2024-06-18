package com.green.yogizogi.dto;

import com.green.yogizogi.constant.OrderStatus;
import lombok.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderAndReviewDTO {
    private Long order_id;
    private LocalDateTime regDate;
    private Long store_id;
    private String storeName;
    private int deliveryTip;
    private int totalPrice;
    private OrderStatus orderStatus;
    private String address1;
    private String address2;
    private String address3;

    //리뷰 관련 dto
    private Long reviewnum;
    private String email;
    private int grade;
    private String text;
    private LocalDateTime reviewModDate;


    //storeImg를 불러오기 위한 필드값
    private String uuid;
    private String imgName;
    private String path;

    //review 작성을 위한 맴버 아이디 및 맴버 닉네임
    private String nickname;
    private Long member_id;
    @Builder.Default
    List<OrderMenuDTO> orderMenuDTOList = new ArrayList<>();
    public void addMenuDTO(OrderMenuDTO orderMenuDTO) {
        orderMenuDTOList.add(orderMenuDTO);
    }

    public String getImageURL(){
        try {
            return URLEncoder.encode(path+"/"+uuid+"_"+imgName,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbnailURL(){
        try {
            return URLEncoder.encode(path+"/s_"+uuid+"_"+imgName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
