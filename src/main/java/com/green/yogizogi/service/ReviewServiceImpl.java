package com.green.yogizogi.service;

import com.green.yogizogi.dto.ReviewDTO;
import com.green.yogizogi.entity.Member;
import com.green.yogizogi.entity.Order;
import com.green.yogizogi.entity.Review;
import com.green.yogizogi.entity.Store;
import com.green.yogizogi.repository.MemberRepository;
import com.green.yogizogi.repository.OrderRepository;
import com.green.yogizogi.repository.ReviewRepository;
import com.green.yogizogi.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<ReviewDTO> getListOfStore(Long storeId) {
        Store store = Store.builder().id(storeId).build();
        List<Review> result = reviewRepository.findByStore(store); //review가 아닌 ReviewDTO
        //Review가 담긴 list를 ReviewDTO가 담긴 리스트로 변환
        //List<ReviewDTO>
        return result.stream().map(review -> entityToDto(review)).collect(Collectors.toList());
    }                        //형태를 바꾸고 싶을땐 map을 쓴다.

    @Override
    public List<ReviewDTO> getListOfEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        List<Review> reviewList = reviewRepository.findByMember(member);
        return reviewList.stream().map(review -> entityToDto(review)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO reviewDTO) {
        Review review = dtoToEntity(reviewDTO);
        Member member = memberRepository.findById(reviewDTO.getMemberId()).get();
        Store store = storeRepository.findById(reviewDTO.getStoreId()).get();
        Order order = orderRepository.findById(reviewDTO.getOrderId()).get();
        review.setMember(member);
        review.setStore(store);
        review.setOrder(order);
        reviewRepository.save(review);
        return review.getReviewnum();
    }

    @Override
    public void modify(ReviewDTO reviewDTO) {
        //수정할 레코드를 조회
        Optional<Review> result = reviewRepository.findById(reviewDTO.getReviewnum()); //리턴할 타입 꼭 확인할 것
        //isPresent() : result가 있으면 true리턴, 없으면 false리턴
        if (result.isPresent()){ //조회한 애로 review를 받아야한다.
            //result.get() : Review객체 리턴
            Review review = result.get();
            //평점과, 리뷰내용만 수정가능 (사용자가 입력한 걸로 수정해줌)
            review.changeGrade(reviewDTO.getGrade());
            review.changeText(reviewDTO.getText());
            //update실행 save는 객체가 없을때는 insert가 되고, 있을때는 update가 된다.
            reviewRepository.save(review); //수정도 save로 한다.
        }
    }

    @Override
    public void remove(Long reviewnum) {
        reviewRepository.deleteById(reviewnum);
    }

    @Override
    public Double storeAvgGrade(Long StoreId) {
        Store store = storeRepository.findById(StoreId).get();
        Double avgGrade = reviewRepository.totalStarEvg(store);
        return avgGrade;
    }
}
