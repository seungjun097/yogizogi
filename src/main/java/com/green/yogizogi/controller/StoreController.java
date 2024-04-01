package com.green.yogizogi.controller;

import com.green.yogizogi.common.PageRequestDTO;
import com.green.yogizogi.dto.CartDTO;
import com.green.yogizogi.dto.CartMenuDTO;
import com.green.yogizogi.dto.StoreDTO;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.service.CartMenuService;
import com.green.yogizogi.service.CartService;
import com.green.yogizogi.service.MemberService;
import com.green.yogizogi.service.StoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;
    private final MemberService memberService;
    private final CartMenuService cartMenuService;
    private final CartService cartService;

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

    @GetMapping("/list")
    public String list(PageRequestDTO dto, Model model) {
        model.addAttribute("result", storeService.storeListAll(dto));
        return "/store/storelist";
    }


    @GetMapping("/detail/{storeId}")
    @Transactional
    public String storeDetatilview(@PathVariable("storeId") Long storeId,
                                   @AuthenticationPrincipal User user,
                                   Model model) {
        System.out.println("가게 아이디 조회 : "+storeId);
        //로그인한 유저 아이디로 카트DTO 불러오기
        String email = user.getUsername();
        CartDTO cartDTO = cartService.cartFindByMemberEmail(email);
        model.addAttribute("cartDTO", cartDTO);
        //스토어 아이디로 스토어DTO 불러오기
        StoreDTO storeDTO = storeService.findStore(storeId);
        model.addAttribute("storeDTO", storeDTO);

        return "store/storedetail";
    }

    //카트 및 카트 메뉴 생성
    @PostMapping("/cart")
    public @ResponseBody ResponseEntity cartRegister(@RequestBody CartMenuDTO cartMenuDTO,
                                                     @AuthenticationPrincipal User user) {
        String email = user.getUsername();
        Long cartId = cartMenuService.CartMenuRegister(email, cartMenuDTO);
        //여기까지 저장은 되는데
        CartDTO cartDTO = cartService.cartFindById(cartId);
        return new ResponseEntity<CartDTO> (cartDTO, HttpStatus.OK);
    }

    @DeleteMapping("/cart/delete/{cartMenuId}")
    public @ResponseBody ResponseEntity cartMenuDelete(@PathVariable("cartMenuId") Long cartMenuId) {
        Long cartId = cartMenuService.cartMenuDelete(cartMenuId);
        CartDTO cartDTO = cartService.cartFindById(cartId);
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK);
    }

}
