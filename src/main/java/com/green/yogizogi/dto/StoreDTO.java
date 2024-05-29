package com.green.yogizogi.dto;

import com.green.yogizogi.constant.StoreCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO {
    private Long member_id;
    private Long id;
    private StoreCategory category;
    private String storeName;
    private String storeAddress1;
    private String storeAddress2;
    private String storeAddress3;
    private String storePhone;
    private String openingTime;
    private String closingTime;
    private int minDelivery;
    private int deliveryTime;
    private int deliveryTip;
    private String storeDes;
    //가게 등록 시 이미지 저장주소
    private String uuid;
    private String imgName;
    private String path;
    private int isLikes;

    @Builder.Default
    List<MenuDTO> menuDTOList = new ArrayList<>();

    public void addMenuDTO (MenuDTO menuDTO) {
        menuDTOList.add(menuDTO);
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