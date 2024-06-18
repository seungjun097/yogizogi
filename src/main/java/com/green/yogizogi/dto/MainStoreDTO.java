package com.green.yogizogi.dto;

import com.green.yogizogi.constant.StoreCategory;
import lombok.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MainStoreDTO {
    private Long id;
    private StoreCategory category;
    private String store_name;
    private String store_address1;
    private String store_address2;
    private String store_address3;
    private String opening_time;
    private String closing_time;
    private Integer min_delivery;
    private Integer delivery_time;
    private Integer delivery_tip;

    //이미지 주소
    private String uuid;
    private String imgName;
    private String path;
    private String storeDes;
    //별점
    private Double avgGrade;
    //스토어 리뷰수
    private Long reviewCount;

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
