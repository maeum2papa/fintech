package fintech.loans.service;

import fintech.loans.domain.Checker;
import fintech.loans.dto.CheckSaveRequestDto;
import fintech.loans.dto.eum.InterestRateEnum;
import fintech.loans.dto.eum.LoanKindEnum;
import fintech.loans.dto.eum.StatusEnum;
import fintech.loans.repository.CheckRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CheckServiceImpl implements CheckService{

    private final CheckRepository checkRepository;

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


            //월 상환 원금 구하기 = 대출희망금액 / 대출상환기간(개월)
            BigDecimal monthlyRepaymentAmount = BigDecimal.valueOf(checker.getAmount())
                    .divide(
                            BigDecimal.valueOf(checker.getLoanRepaymentPeriod()),
                            RoundingMode.UP
                    );
            checker.setMonthlyRepaymentAmount(monthlyRepaymentAmount.longValue());


            //월 상환 이자 구하기 = 월 상환원금 * (금리 / 100)
            BigDecimal monthlyRepaymentInterest = BigDecimal.valueOf(checker.getMonthlyRepaymentAmount())
                    .multiply(BigDecimal.valueOf(basicInterestRate / 100));

            checker.setMonthlyRepaymentInterest(
                    monthlyRepaymentInterest.setScale(0,RoundingMode.UP)
                                            .longValue()
            );


            //월 상환 원리금 = 월 상환원금 + 월 상환이자
            checker.setMonthlyRepaymentOfPrincipalAndInterest(
                    checker.getMonthlyRepaymentAmount() + checker.getMonthlyRepaymentInterest()
            );


            //총 대출 이자금  = 월 상환이자 * 대출상환기간(개월)
            checker.setTotalLoanInterest(checker.getMonthlyRepaymentInterest() * checker.getLoanRepaymentPeriod());


            //(타대출연원리금 + (월상환원리금 *12))/연소득 < DSR = true;
            BigDecimal DSR = BigDecimal.valueOf(checker.getOtherYearPrincipalAndInterrest() + (checker.getMonthlyRepaymentOfPrincipalAndInterest() * 12))
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
        Checker checker = findChecker.orElse(null);

        return checker;
    }

    @Override
    public Checker contractLoan() {
        return null;
    }

}
