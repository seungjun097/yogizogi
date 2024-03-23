package com.green.yogizogi.controller;

import com.green.yogizogi.dto.UploadResultDTO;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {

    @Value("${org.store.upload.path}") //실제경로 - application.properties 설정참조
    private String uploadPath;

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(@RequestParam(value = "uploadFiles")MultipartFile[] uploadFiles){

        List<UploadResultDTO> resultDTOLists = new ArrayList<>();

        for (MultipartFile uploadFile: uploadFiles){

            //이미지 파일만 업로드 가능
            if (uploadFile.getContentType().startsWith("image") == false){
                log.warn("이미지 파일이 아닙니다.");
                //403에러 : 클라이언트 요청을 이해했지만 서버가 요청 거부, 요청 리소스에 대한 권한이 없을때
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            String OriginalName = uploadFile.getOriginalFilename();
            log.info("OriginalName : " + OriginalName);
            String FileName = OriginalName.substring(OriginalName.lastIndexOf("\\")+1);
            log.info("fileName : " + FileName);

            //날짜폴더 생성   //"2024\03\11"
            String folderpath = makeFolder();
            //uuid생성
            String uuid = UUID.randomUUID().toString();
            String saveNamem = uploadPath+File.separator+folderpath+File.separator+uuid+"_"+FileName;
            //c://upload\2024\03\11\imgkdfkdjfdfdkjdjfkd_dog.jpg
            //패스객체 생성
            Path savePath = Paths.get(saveNamem);
            try {
                //파일저장
                uploadFile.transferTo(savePath);
                //원본파일 저장할때 썸네일 생성
                String thumbnailSaveName = uploadPath + File.separator + folderpath + File.separator + "s_" + uuid + "_"+FileName;
                File thumbnailFile = new File(thumbnailSaveName);
                //썸네일 생성
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100, 100);
                resultDTOLists.add(new UploadResultDTO(FileName,uuid,folderpath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new ResponseEntity<>(resultDTOLists, HttpStatus.OK);
    }
    //날짜폴더 생성메소드
    private String makeFolder(){
        //오늘 날짜객체를 "2024/03/11" 문자로 str 할당
        //"2024/03/11" --> 2024\03\11
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderpath = str.replace("/", File.separator); //윈도우나 맥이나 프로그램에 파일로 들어가는 경로
        File uploadPathFile = new File(uploadPath, folderpath);
        if (uploadPathFile.exists()==false){
            uploadPathFile.mkdirs(); //존재하지 않으면 폴더를 만들어줘라
        }
        return folderpath; //존재하면 경로대로 폴더를 다 만들어라

    }
    @GetMapping("/display")
    public ResponseEntity<byte[]> getFiles(@RequestParam(value = "fileName") String fileName){
        ResponseEntity<byte[]> result = null;
        String srcFileName = null;
        try {
            srcFileName = URLDecoder.decode(fileName,"utf-8");
            File file = new File(uploadPath+File.separator+srcFileName);
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            //파일의 MIME타입을 헤더에 추가하기
            //Files.probeContentType 파일의 마임타입 검사
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        } catch (Exception e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); //서버에러 500번대
        }
        return result;
    }
    @PostMapping("/removeFile") //파일 삭제하기
    public ResponseEntity<Boolean> removeFile(@RequestParam(value = "fileName") String fileName){
        String srcFilename = null;
        try {
            srcFilename = URLDecoder.decode(fileName, "UTF-8");
            File file = new File(uploadPath+File.separator+srcFilename);
            boolean result = file.delete(); //delete호출 boolean타입으로 받는다
            //파일을 지웠으면 썸네일도 지워줘야한다.
            File thumbnail = new File(file.getParent(),"s_"+file.getName()); //위의 file의 경로를 받겠다.
            result = thumbnail.delete();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
