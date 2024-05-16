package com.green.yogizogi.service;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private IamportClient api;

    public PaymentService() {
        this.api = new IamportClient("3203513003174067", "e4DO0V56Rl76Nd2Khr2PUbwwJ60pipNllSo6hQQ6jRDSxLvtJqVdx9X4z14GtQk7Tp67oo2qRgRCvDN3");

    }
}
