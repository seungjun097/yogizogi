package com.green.yogizogi.controller;

import com.green.yogizogi.common.PageRequestDTO;
import com.green.yogizogi.constant.StoreCategory;
import com.green.yogizogi.dto.*;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
    @GetMapping("/{category}/{address1}/{sort}/{search}")
    public String store(@PathVariable("category") StoreCategory category,
                        @PathVariable("address1") int address1,
                        @PathVariable("sort") String sort,
                        @PathVariable("search") String search,
                        Model model) {
        List<MainStoreDTO> storeList = storeService.StoreSearch(category, address1, sort, search);
        model.addAttribute("category", category);
        model.addAttribute("sort", sort);
        model.addAttribute("storeDTOList", storeList);
        return "main";
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
        String email = principalDetails.getUsername();
        CartDTO cartDTO = cartService.cartFindByMemberEmail(email);
        model.addAttribute("cartDTO", cartDTO);
        //스토어 아이디로 스토어DTO 불러오기
        StoreDTO storeDTO = storeService.findStore(storeId);
        model.addAttribute("storeDTO", storeDTO);
        List<ReviewDTO> reviewDTOList = reviewService.getListOfStore(storeId);
        Double starAvg = reviewService.storeAvgGrade(storeId);
        if(starAvg == null) {
            starAvg = 0.0;
        }
        model.addAttribute("starAvg", starAvg);
        model.addAttribute("reviewDTOList", reviewDTOList);
        String likes = storeService.isLikes(storeId, email);
        model.addAttribute("likes", likes);
        return "store/storedetail";
    }
    @PostMapping("/likes")
    public @ResponseBody ResponseEntity<String> likes(@RequestBody LikeDTO likeDTO,
                                                      @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(principalDetails == null) {
            return ResponseEntity.badRequest().body("회원만 가능합니다");
        }

        String userEmail = principalDetails.getUsername();
        Member member = memberService.userFindEmail(userEmail);
        Long userId = member.getId();
        storeService.likes(likeDTO.getStoreId(), likeDTO.getLikes(), userId);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/search")
    public String searchPage() {
        return "store/mainfirst";
    }

    // 찜한 가게 목록
    @GetMapping("/likesList")
    public String likes(Model model, @AuthenticationPrincipal UserDetails principal) {
        System.out.println("좋아요리스트작동");

        if (principal == null) {
            System.out.println("비로그인");
        } else {
            System.out.println("로그인");
            List<MainStoreDTO> storeList = storeService.likeList(principal.getUsername());
            model.addAttribute("storeDTOList", storeList);
        }
        return "main";
    }
}
