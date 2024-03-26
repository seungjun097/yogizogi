package com.green.yogizogi.dto;

import com.green.yogizogi.constant.MenuType;
import com.green.yogizogi.constant.SellStatus;
import com.green.yogizogi.entity.Store;
import lombok.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
    private Long id;
    private Long store_id;

    private MenuType menuType;
    private String menuName;
    private int menuPrice;
    private String menuDesc;
    private SellStatus sellStatus;
    //메뉴 이미지
    private String uuid;
    private String imgName;
    private String path;

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
