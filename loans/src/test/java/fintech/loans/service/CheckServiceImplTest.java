package fintech.loans.service;

import fintech.loans.domain.Checker;
import fintech.loans.domain.Repay;
import fintech.loans.dto.eum.InterestRateEnum;
import fintech.loans.dto.eum.LoanKindEnum;
import fintech.loans.dto.eum.StatusEnum;
import fintech.loans.repository.CheckRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CheckServiceImplTest {

    @Autowired
    CheckRepository checkRepository;

    @Test
    void 대출심사(){

        Checker ck = Checker
                .builder()
                .name("오서방")
                .phone("01040768888")
                .email("oh@naver.com")
                .address("서울")
                .addressDetail("마포구")
                .loanKind(LoanKindEnum.HOUSE)
                .otherYearPrincipalAndInterrest(0L)
                .LTV(0.8)
                .DSR(0.8)
                .income(100000000L)
                .asset(100000000L)
                .amount(430000000L)
                .loanRepaymentPeriod(360)
                .interestRateKind(InterestRateEnum.FIXED)
                .status(StatusEnum.CHECK)
                .build();

        Checker save = checkRepository.save(ck);

        Optional<Checker> findChecker = checkRepository.findById(save.getId());
        Checker checker = findChecker.orElse(null);

        if(checker != null && checker.getStatus() == StatusEnum.CHECK){

            double basicInterestRate = 3.82;

            //고정금리 선택 시 가산금리 적용
            if(checker.getInterestRateKind() == InterestRateEnum.FIXED){
                basicInterestRate += 0.18;
            }

            checker.setInterestRate(basicInterestRate);

            //원리금균등상환
            Long repayment = checker.getAmount();
            Long balanceAmount = 0L;
            Long totalLoanInterest = 0L;
            ArrayList<Repay> repaymentList = new ArrayList<>();

            //월 이자율 = (0.38 / 100)/12
            BigDecimal monthInterest = BigDecimal.valueOf(basicInterestRate)
                    .divide(BigDecimal.valueOf(100),20, RoundingMode.HALF_UP)
                    .divide(BigDecimal.valueOf(12),20,RoundingMode.HALF_UP);

            for (int i = 1; i < checker.getLoanRepaymentPeriod()+1; i++) {

                // 월이자계산기 = 잔금 * 월 이자율
                BigDecimal interest = BigDecimal.valueOf(repayment)
                                .multiply(monthInterest);

                //월상환계산기
                BigDecimal basicInterest = BigDecimal.valueOf(checker.getAmount()).multiply(monthInterest);

                BigDecimal add = BigDecimal.valueOf(1).add(monthInterest);
                BigDecimal pow = add.pow(-checker.getLoanRepaymentPeriod(), MathContext.DECIMAL128);
                BigDecimal subtract = BigDecimal.valueOf(1).subtract(pow);

                BigDecimal amount = basicInterest.divide(subtract, 0, RoundingMode.HALF_UP);

                Long  repaymentAmount = amount.longValue() - interest.longValue();

                balanceAmount = balanceAmount + amount.longValue();
                repayment = repayment - repaymentAmount;

                Repay repay = Repay.builder()
                        .round(i)
                        .monthlyRepaymentInterest(interest.longValue())
                        .monthlyRepaymentAmount(repaymentAmount)
                        .monthlyRepaymentOfPrincipalAndInterest(amount.longValue())
                        .totalRepaymentAmount(repayment)
                        .balanceAmount(balanceAmount)
                        .build();

                repaymentList.add(repay);

                totalLoanInterest = totalLoanInterest + interest.longValue();

            }

            System.out.println("totalLoanInterest = " + totalLoanInterest);


        }

    }
    
    @Test
    void 계산(){

        BigDecimal monthlyRepaymentInterest = BigDecimal.valueOf(4333000)
                .multiply(BigDecimal.valueOf(0.38 / 100));

        //16465.4000
        System.out.println(monthlyRepaymentInterest.setScale(0,RoundingMode.UP));

    }
    
}