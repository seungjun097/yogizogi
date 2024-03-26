package com.green.yogizogi.controller;

import com.green.yogizogi.common.PageRequestDTO;
import com.green.yogizogi.common.PageResultDTO;
import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.entity.Store;
import com.green.yogizogi.service.MemberService;
import com.green.yogizogi.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;
    private final MemberService memberService;

    //가게추가 페이지 이동
    @GetMapping("/register")
    public String StoreUpdate(@AuthenticationPrincipal User user, Model model) {
        if(user==null) {
            return "main";
        }else {
            Member member = memberService.userFindEmail(user.getUsername());
            model.addAttribute("member_id", member.getId());
            return "store/storeregister";
        }
    }
    //가게추가 과정
    @PostMapping("/register")
    public String StoreUpdateProc(StoreDTO storeDTO) {
        System.out.println(storeDTO.toString());
        Long storeId = storeService.StoreRegister(storeDTO);
        System.out.println("가게 등록 성공 : "+storeId);
        return "main";
    }


    @GetMapping("/detail/{storeId}")
    public String storeDetatilview(@PathVariable("storeId") Long storeId, Model model) {
        System.out.println("가게 아이디 조회 : "+storeId);
        StoreDTO storeDTO = storeService.findStore(storeId);
        model.addAttribute("storeDTO", storeDTO);
        return "store/storedetail";
    }
    @GetMapping("/search")
    public String search(@RequestParam(name = "searchType", required = false) String searchType,
                         @RequestParam(name = "keyword", required = false) String keyword,
                         @ModelAttribute("pageRequest") PageRequestDTO pageRequestDTO,
                         Model model) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize());
        Page<Store> searchResults = null;

        if (searchType != null && keyword != null) {
            switch (searchType) {
                case "category":
                    searchResults = storeService.searchByCategory(keyword, pageable);
                    break;
                case "storeName":
                    searchResults = storeService.searchByStoreName(keyword, pageable);
                    break;
                case "deliveryTip":
                    searchResults = storeService.searchByDeliveryTip(keyword, pageable);
                    break;
                case "deliveryTime":
                    searchResults = storeService.searchByDeliveryTime(keyword, pageable);
                    break;
                default:
                    // 처리할 수 없는 검색 유형일 경우
                    break;
            }
        }

        // 검색 결과를 모델에 추가
        model.addAttribute("searchResults", searchResults);

        // 검색 결과를 보여줄 뷰의 이름을 반환
        return "/storelist";
    }

}