package com.green.yogizogi.common;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Data
public class PageResultDTO<DTO, EN> {
    //dto리스트
    private List<DTO> dtoList;
    //총페이지번호
    private int totalpage;
    //현재페이지번호
    private int page;
    //목록사이즈
    private int size;
    //시작페이지 번호, 끝페이지 번호
    private int start, end;
    private boolean prev, next;
    //페이지 번호목록
    private List<Integer> pageList;
    //주소찾기용
    private String type;
    private String keyword;
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        //dtoList : 게시글
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        //전체 페이지수
        totalpage = result.getTotalPages();
        makePageList(result.getPageable());
    }
    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber()+1;
        int tempEnd = (int) Math.ceil(page/10.0)*10;
        //1페이지일 때 Math.ceil(0.1)*10 = 10
        //11페이지일 때 20
        //21페이지일 떄 30...
        start = tempEnd - 9;
        //임시 마지막 번호가 전체 페이지 번호보다 작으면 임시마지막 번호 아니면 토탈페이지
        end = totalpage > tempEnd ? tempEnd : totalpage;
        prev = start > 1;
        next = totalpage > tempEnd;
        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }
}