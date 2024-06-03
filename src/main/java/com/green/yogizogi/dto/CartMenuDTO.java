package com.green.yogizogi.dto;

import lombok.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartMenuDTO {
    private Long cartMenu_id;
    private Long member_id;
    private Long menu_id;
    private String menu_name;
    private int menu_price;

    private String uuid;
    private String imgName;
    private String path;

    private int count;

    List<CartMenuOptionDTO> cartMenuOptionDTOList = new ArrayList<>();

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
