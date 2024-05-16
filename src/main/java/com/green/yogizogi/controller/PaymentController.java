package com.green.yogizogi.controller;

import com.green.yogizogi.dto.PaymentDTO;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private IamportClient iamportClient;

    public PaymentController() {
        this.iamportClient = new IamportClient("3203513003174067",
                "e4DO0V56Rl76Nd2Khr2PUbwwJ60pipNllSo6hQQ6jRDSxLvtJqVdx9X4z14GtQk7Tp67oo2qRgRCvDN3");
    }

    @PostMapping("/verification")
    private IamportResponse<Payment> paymentByImpUid(@RequestBody PaymentDTO paymentDTO) throws IamportResponseException, IOException {
        System.out.println(paymentDTO);
        return iamportClient.paymentByImpUid(paymentDTO.getImp_uid());
    }
}
