package fintech.loans.service;

import fintech.loans.domain.Counsel;
import fintech.loans.repository.CounselRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CounselServiceImplTest {
    
    @Autowired
    CounselRepository counselRepository;
    
    @Test
    void findById() {
        Optional<Counsel> findCounsel = counselRepository.findById(2L);
        System.out.println("findCounsel.orElse(null) = " + findCounsel.orElse(null));
    }
}