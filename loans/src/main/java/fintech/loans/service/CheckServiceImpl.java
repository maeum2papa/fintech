package fintech.loans.service;

import fintech.loans.domain.Checker;
import fintech.loans.domain.Repay;
import fintech.loans.dto.CheckSaveRequestDto;
import fintech.loans.dto.eum.InterestRateEnum;
import fintech.loans.dto.eum.LoanKindEnum;
import fintech.loans.dto.eum.StatusEnum;
import fintech.loans.repository.CheckRepository;
import fintech.loans.repository.RepayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CheckServiceImpl implements CheckService{

    private final CheckRepository checkRepository;
    private final RepayService repayService;
    private final RepayRepository repayRepository;

    @Override
    public Checker saveCheckLoan(CheckSaveRequestDto checkSaveRequestDto) {

        Checker checker = Checker
                .builder()
                .name(checkSaveRequestDto.getName())
                .phone(checkSaveRequestDto.getPhone())
                .email(checkSaveRequestDto.getEmail())
                .address(checkSaveRequestDto.getAddress())
                .addressDetail(checkSaveRequestDto.getAddressDetail())
                .loanKind(checkSaveRequestDto.getLoanKind())
                .otherYearPrincipalAndInterrest(checkSaveRequestDto.getOtherYearPrincipalAndInterrest())
                .LTV(checkSaveRequestDto.getLTV())
                .DSR(checkSaveRequestDto.getDSR())
                .income(checkSaveRequestDto.getIncome())
                .asset(checkSaveRequestDto.getAsset())
                .amount(checkSaveRequestDto.getAmount())
                .loanRepaymentPeriod(checkSaveRequestDto.getLoanRepaymentPeriod())
                .interestRateKind(checkSaveRequestDto.getInterestRateKind())
                .status(StatusEnum.CHECK)
                .build();

        return checkRepository.save(checker);
    }

    @Override
    public Checker checkLoan(Long id) {

        Optional<Checker> findChecker = checkRepository.findById(id);
        Checker checker = findChecker.orElse(null);


        if(checker != null && checker.getStatus() == StatusEnum.CHECK){

            double basicInterestRate = 3.82;

            //고정금리 선택 시 가산금리 적용
            if(checker.getInterestRateKind() == InterestRateEnum.FIXED){
                basicInterestRate += 0.18;
            }

            checker.setInterestRate(basicInterestRate);

            //원리금균등상환 계산
            checker = equalRepaymentOfPrincipalAndInterest(checker);

            //월평균원리금 (상환 원금 + 이자)
            Long averageMonthTotalRepay = (checker.getAmount() + checker.getTotalLoanInterest()) / checker.getLoanRepaymentPeriod();

            //연평균원리금 (월 평균 상환금액계산 * 12)
            Long averageYearTotalRepay = averageMonthTotalRepay * 12;

            //(타대출연원리금 + 연평균원리금)/연소득 < DSR = true;
            BigDecimal DSR = BigDecimal.valueOf(checker.getOtherYearPrincipalAndInterrest() + averageYearTotalRepay)
                    .divide(
                            BigDecimal.valueOf(checker.getIncome())
                            , 3
                            , RoundingMode.UP
                    );

            log.info("DSR = {}",DSR.doubleValue());

            boolean pass = DSR.doubleValue() <= checker.getDSR();


            //주담대일 경우 LTV 체크
            if(checker.getLoanKind() == LoanKindEnum.HOUSE && pass){

                //LTV : (대출금액/담보가치) < LTV = true;
                BigDecimal LTV = BigDecimal.valueOf(checker.getAmount())
                        .divide(
                                BigDecimal.valueOf(checker.getAsset()),
                                3,
                                RoundingMode.UP
                        );

                log.info("LTV = {}",LTV.doubleValue());

                if(LTV.doubleValue() > checker.getLTV() ){
                    pass = false;
                }

            }


            if(pass){
                checker.setStatus(StatusEnum.APPROVED);
            }else{
                checker.setStatus(StatusEnum.REJECTED);
            }

            checker.setExaminationDate(LocalDateTime.now());

        }

        return checker;
    }

    @Override
    public Checker viewCheckLoan(Long id) {

        Optional<Checker> findChecker = checkRepository.findById(id);

        return findChecker.orElse(null);
    }

    @Override
    public Checker contractLoan(Long id) {

        Optional<Checker> findChecker = checkRepository.findById(id);
        Checker checker = findChecker.orElse(null);

        if(checker != null && checker.getStatus() == StatusEnum.APPROVED){
            checker.setContractDate(LocalDateTime.now());
        }else{
            throw new RuntimeException("대출 심사 승인이 필요합니다.");
        }

        return checker;
    }

    /**
     * 원리금균등상환 계산기
     * @param checker
     * @return
     */
    public Checker equalRepaymentOfPrincipalAndInterest(Checker checker){

        //원리금균등상환
        Long repayment = checker.getAmount();
        Long balanceAmount = 0L;
        Long totalLoanInterest = 0L;
        ArrayList<Repay> repaymentList = new ArrayList<>();

        //월 이자율 = (0.38 / 100)/12
        BigDecimal monthInterest = BigDecimal.valueOf(checker.getInterestRate())
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

        checker.setTotalLoanInterest(totalLoanInterest);

        return checker;
    }

}
