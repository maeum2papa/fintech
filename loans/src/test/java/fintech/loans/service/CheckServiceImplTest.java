package fintech.loans.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class CheckServiceImplTest {
    
    @Test
    void 계산(){

        BigDecimal monthlyRepaymentInterest = BigDecimal.valueOf(4333000)
                .multiply(BigDecimal.valueOf(0.38 / 100));

        //16465.4000
        System.out.println(monthlyRepaymentInterest.setScale(0,RoundingMode.UP));

    }
    
}