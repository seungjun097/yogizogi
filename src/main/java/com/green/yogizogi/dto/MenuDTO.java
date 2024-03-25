package com.green.yogizogi.dto;

import com.green.yogizogi.constant.MenuType;
import com.green.yogizogi.constant.SellStatus;
import com.green.yogizogi.entity.Store;
import lombok.*;

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
}
