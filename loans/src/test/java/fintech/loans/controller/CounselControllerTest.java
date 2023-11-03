package fintech.loans.controller;

import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import fintech.loans.domain.Counsel;
import fintech.loans.dto.CounselRequestDto;
import fintech.loans.dto.CounselResponseDto;
import fintech.loans.service.CounselServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CounselControllerTest {

    @Autowired
    CounselServiceImpl counselService;

    @Test
    void CounselTest(){
//
//        Counsel saveCounsel = counselService.save(new CounselRequestDto("홍길동","01040768612",null,null));
//
//        System.out.println("saveCounsel = " + saveCounsel);
    }

}