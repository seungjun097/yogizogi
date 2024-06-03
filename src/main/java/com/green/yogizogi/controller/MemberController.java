package com.green.yogizogi.controller;

import com.green.yogizogi.dto.SignupDTO;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.service.MemberService;
import com.green.yogizogi.service.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/")
public class MemberController {
    private final MemberService memberService;

    //가입 페이지 이동;
    @GetMapping("/signup")
    public String getSignUp() {
        return "/member/signup";
    }

    @GetMapping("/edit")
    public String getEdit(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        Member member = memberService.userFindEmail(principalDetails.getUsername());
        model.addAttribute("member", member);
        return "/member/edit";
    }

    @PostMapping("/edit")
    public String postEdit(@Valid SignupDTO signupDTO, Errors errors, Model model) {
        System.out.println(signupDTO.toString());
        Long result = memberService.edit(signupDTO);
        System.out.println(result);
        return "/main";
    }


    //가입 처리 및 결과 리턴
    @PostMapping("/signup")
    public String postSignUp(@Valid SignupDTO signupDTO, Errors errors, Model model) {

        //기입한 내용에 문제가 있어 에러메세지가 존재하는 경우
        if(errors.hasErrors()) {
            //사용자로부터 입력받은 데이터를 유지하기 위해 기입한 내용DTO를 저장
            model.addAttribute("signupDTO", signupDTO);

            for(FieldError error : errors.getFieldErrors()) {
                model.addAttribute(error.getField(), error.getDefaultMessage());
            }
            return "/member/signup";
        }

        Long result = memberService.signUp(signupDTO);
        System.out.println(result);
        return "/member/login";
    }

    @PostMapping("/emailcheck")
    public ResponseEntity<String> memberMailCheck(SignupDTO signupDTO) {
        String result =memberService.userEmailChk(signupDTO.getEmail());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/login")
    public String loginMember() {
        return "/member/login";
    }

    @GetMapping("/login/error")
    public String loginMember(Model model) {
        model.addAttribute("loginErrorMsg",
                "아이디 또는 비밀번호를 확인해주세요");
        return "/member/login";
    }
}