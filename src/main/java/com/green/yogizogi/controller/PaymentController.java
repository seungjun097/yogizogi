package com.green.yogizogi.controller;

import com.green.yogizogi.dto.PaymentDTO;
import com.green.yogizogi.service.OrderService;
import com.green.yogizogi.service.RefundService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController{

    private final RefundService refundService;
    private final OrderService orderService;

    private IamportClient iamportClient;

    @Value("${imp.api.key}")
    private String apikey;
    @Value("${imp.api.secretKey}")
    private String secretKey;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apikey, secretKey);
    }
    @PostMapping("/verification")
    private IamportResponse<Payment> paymentByImpUid(@RequestBody PaymentDTO paymentDTO) throws IamportResponseException, IOException {
        System.out.println(paymentDTO);
        return iamportClient.paymentByImpUid(paymentDTO.getImp_uid());
    }

    @PostMapping("/cancel")
    public @ResponseBody ResponseEntity orderCancel(@RequestBody Long order_id) throws IOException {
        String token = refundService.getToken(apikey, secretKey);
        String merchant_uid = orderService.findMerchantId(order_id);
        refundService.refundRequest(token, merchant_uid, "cancel");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
