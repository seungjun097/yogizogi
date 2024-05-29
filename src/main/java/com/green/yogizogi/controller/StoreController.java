package com.green.yogizogi.controller;

import com.green.yogizogi.common.PageRequestDTO;
import com.green.yogizogi.constant.StoreCategory;
import com.green.yogizogi.dto.CartDTO;
import com.green.yogizogi.dto.CartMenuDTO;
import com.green.yogizogi.dto.ReviewDTO;
import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;
    private final MemberService memberService;
    private final CartService cartService;
    private final ReviewService reviewService;

    //카테고리 주소로 주위가게리스트 검색
    @GetMapping("/{category}/{address1}")
    public String store(@PathVariable("category") StoreCategory category, @PathVariable("address1") int address1, Model model) {

        System.out.println("category.address1 = " + category +","+ address1);
        List<StoreDTO> storeList = storeService.getStoresByCategoryAndAddress(category, address1 / 100);
        model.addAttribute("storeList", storeList);
        System.out.println("storeList: " + storeList);

        return "/store/mainlist";
    }

    @GetMapping("/")
    public String index() {
        return "/store/storelist";
    }
    @GetMapping("/map")
    public String map(){
        return "/store/map";
    }

    //가게추가 페이지 이동
    @GetMapping("/register")
    public String StoreUpdate(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if(principalDetails==null) {
            return "redirect:/";
        }else {
            Member member = memberService.userFindEmail(principalDetails.getUsername());
            model.addAttribute("member_id", member.getId());
            return "store/storeRegister";
        }
    }
    //가게추가 과정
    @PostMapping("/register")
    public String StoreUpdateProc(StoreDTO storeDTO) {
        System.out.println(storeDTO.toString());
        Long storeId = storeService.StoreRegister(storeDTO);
        System.out.println("가게 등록 성공 : "+storeId);
        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(PageRequestDTO dto, Model model) {
        System.out.println("검색컨트롤러작동");
        System.out.println(dto.toString());
        model.addAttribute("storeList", storeService.storeListAll(dto));
        return "/store/maintest";
    }

    @GetMapping("/detail2/{storeId}")
    @Transactional
    public String storeDetatilview2(@PathVariable("storeId") Long storeId,
                                   @AuthenticationPrincipal PrincipalDetails principalDetails,
                                    Model model) {
        boolean memberLogin = true;
        if (principalDetails == null) {
            memberLogin = false;
            model.addAttribute("memberLogin", memberLogin);
            return "/member/login";
        }
        System.out.println("가게 아이디 조회 : "+storeId);
        //로그인한 유저 아이디로 카트DTO 불러오기
        String email = principalDetails.getName();
        CartDTO cartDTO = cartService.cartFindByMemberEmail(email);
        model.addAttribute("cartDTO", cartDTO);
        //스토어 아이디로 스토어DTO 불러오기
        StoreDTO storeDTO = storeService.findStore(storeId);
        model.addAttribute("storeDTO", storeDTO);
        model.addAttribute("memberLogin", memberLogin);
        List<ReviewDTO> reviewDTOList = reviewService.getListOfStore(storeId);
        Double starAvg = reviewService.storeAvgGrade(storeId);
        if(starAvg == null) {
            starAvg = 0.0;
        }
        model.addAttribute("starAvg", starAvg);
        model.addAttribute("reviewDTOList", reviewDTOList);
        return "store/storedetail2";
    }
}
