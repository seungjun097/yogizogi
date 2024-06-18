package com.green.yogizogi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO {
    private String fileName;
    private String uuid;
    private String folderPath;
    
    public String getImageURL(){ //파일과 연관된 모든 (이미지)
        try {
            return URLEncoder.encode(folderPath+"/"+uuid+"_"+fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbnailURL(){ //파일과 연관된 모든 (썸네일)
        try {
            return URLEncoder.encode(folderPath+"/"+uuid+"_"+fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
