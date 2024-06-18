package com.green.yogizogi.controller;

import com.green.yogizogi.dto.ReviewDTO;
import com.green.yogizogi.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService; //final

    //http:localhost:8081/reviews/50/all 파라미터(?) 생략하고 쓸 수 있다.
    @GetMapping("/{storeId}/all")
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("storeId") Long storeId){
        List<ReviewDTO> reviewDTOList = reviewService.getListOfStore(storeId);
        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDTO reviewDTO){
        Long reviewnum = reviewService.register(reviewDTO);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(@PathVariable("reviewnum") Long reviewnum,
                                             @RequestBody ReviewDTO reviewDTO){
        reviewService.modify(reviewDTO);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}/{reviewnum}")
    public ResponseEntity<Long> removeReview(@PathVariable("reviewnum") Long reviewnum){
        reviewService.remove(reviewnum);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

}
