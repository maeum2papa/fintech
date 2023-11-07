package fintech.loans.service;

import fintech.loans.domain.Counsel;
import fintech.loans.dto.CounselRequestDto;
import fintech.loans.repository.CounselRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
@Rollback(value = false)
class CounselServiceImplTest {
    
    @Autowired
    CounselRepository counselRepository;


    @PostConstruct
    void init(){

        System.out.println("init 실행");

        Counsel counsel = Counsel.builder()
                .name("홍길동")
                .phone("01040768612")
                .email("hong@kakao.com")
                .memo("메모")
                .build();

        counselRepository.save(counsel);

    }

    
    @Test
    void 아이디로상담조회() {
        Optional<Counsel> findCounsel = counselRepository.findById(2L);
        System.out.println("findCounsel.orElse(null) = " + findCounsel.orElse(null));
    }

    @Test
    void 컨설팅후테스트() {

        Counsel findCounsel = counselRepository.findById(1L).get();
        findCounsel.setCounselDate(LocalDateTime.now());
        System.out.println("findCounsel = "+findCounsel);

    }



}