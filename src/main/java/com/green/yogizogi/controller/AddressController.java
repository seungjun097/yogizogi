package com.green.yogizogi.controller;

import com.green.yogizogi.dto.AddressDTO;
import com.green.yogizogi.entity.Address;
import com.green.yogizogi.service.AddressService;
import com.green.yogizogi.service.PrincipalDetails;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AddressController {
  
    private final AddressService addressService;


    @PostMapping("/address/save")
    public @ResponseBody ResponseEntity getMemberByEmail(@RequestBody AddressDTO addressDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        String userEmail = principalDetails.getUsername(); // 현재 로그인한 사용자의 이메일을 가져옴
        Long addressId;
        try {
            addressId = addressService.getMemberByEmail(addressDTO, userEmail);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(addressId, HttpStatus.OK);
    }

    @GetMapping("/address")
    public String AddressForm(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        String userEmail = principalDetails.getUsername();
        model.addAttribute("addressDTO", new AddressDTO());
        List<AddressDTO> addressDTOList = addressService.addressList(userEmail);
        model.addAttribute("addressDTOList", addressDTOList);
        return "member/addresslist";
    }

    @PostMapping("/address/add")
    @ResponseBody
    public ResponseEntity<Long> addAddress(@RequestBody AddressDTO addressDTO) {
        // 주소 추가 로직을 여기에 구현
        Long addressId = addressService.register(addressDTO);
        // 주소를 데이터베이스에 저장하거나 다른 작업을 수행할 수 있습니다.

        // 예시로서 간단하게 주소를 반환하도록 하겠습니다.
        return new ResponseEntity<>(addressId, HttpStatus.OK);
    }

}
