package fintech.loans.service;


import fintech.loans.domain.Checker;
import fintech.loans.domain.Repay;
import fintech.loans.dto.RepayRequestDto;
import fintech.loans.dto.eum.InterestRateEnum;
import fintech.loans.repository.CheckRepository;
import fintech.loans.repository.RepayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RepayServiceImpl implements RepayService{

    private final RepayRepository repayRepository;
    private final CheckRepository checkRepository;

    @Override
    public void createRepaySchedule(Checker checker) {

        if(checker != null){

            Integer loanRepaymentPeriod = checker.getLoanRepaymentPeriod();
            LocalDate repaymentDate = LocalDate.now();

            for (int i = 1; i < loanRepaymentPeriod+1; i++) {

                Repay monthRepay = checker.getRepays().get((i - 1));

                Repay repay = Repay.builder()
                        .round(i)
                        .checker(checker)
                        .repaymentDate(repaymentDate.plusMonths(i))
                        .monthlyRepaymentAmount(monthRepay.getMonthlyRepaymentAmount())
                        .monthlyRepaymentInterest(monthRepay.getMonthlyRepaymentInterest())
                        .monthlyRepaymentOfPrincipalAndInterest(monthRepay.getMonthlyRepaymentOfPrincipalAndInterest())
                        .build();

                repayRepository.save(repay);
            }

        }else{
            throw new RuntimeException("계약번호 "+checker.getId()+" 대출의 상환 스케줄 생성 오류가 발생하였습니다.");
        }

    }

    @Override
    public List<Repay> getRepays(Long checkId){
        return repayRepository.findAllByCheckerIdOrderByIdAsc(checkId);
    }

    @Override
    public Repay setReapy(RepayRequestDto repayRequestDto) {

        Optional<Checker> findCheckers = checkRepository.findById(repayRequestDto.getCheckId());
        Checker findChecker = findCheckers.orElseThrow(()-> new RuntimeException("계약번호 "+repayRequestDto.getCheckId()+"의 대출을 찾을 수 없습니다."));

        Optional<Repay> findRepays = findChecker.getRepays()
                .stream()
                .filter(repay -> repay.getRound() == repayRequestDto.getRound())
                .findFirst();

        Repay findRepay = findRepays.orElseThrow(() -> new RuntimeException("상환 " + repayRequestDto.getRound() + "회차를 찾을 수 없습니다."));

        log.info("repayRequestDto = {}",repayRequestDto);
        log.info("findChecker = {}",findChecker);
        log.info("findRepay = {}",findRepay);
        
        if(findChecker.getInterestRateKind() == InterestRateEnum.FIXED && findRepay.getInterestRate() == null){

            if(!Objects.equals(repayRequestDto.getMonthlyRepayment(), findRepay.getMonthlyRepaymentOfPrincipalAndInterest())){
                throw new RuntimeException("상환금액은 "+repayRequestDto.getMonthlyRepayment()+"원이 아닌 "+findRepay.getMonthlyRepaymentOfPrincipalAndInterest()+"원 입니다.");
            }

            findRepay.setInterestRate(findChecker.getInterestRate());
            Long balanceAmount = null;

            if(repayRequestDto.getRound() == 1){

                findRepay.setTotalRepaymentAmount(findRepay.getMonthlyRepaymentAmount());

                balanceAmount = findChecker.getAmount() - findRepay.getMonthlyRepaymentAmount();
                findRepay.setBalanceAmount(balanceAmount);

            } else if (repayRequestDto.getRound() > 1) {

                //이전 회차 상환 정보
                Optional<Repay> beforeRepays = findChecker.getRepays()
                        .stream()
                        .filter(repay -> repay.getRound() == (repayRequestDto.getRound() - 1))
                        .findFirst();

                Repay beforeRepay = beforeRepays.orElse(null);
                log.info("beforeRepay = {}",beforeRepay);
                if(beforeRepay == null || beforeRepay.getInterestRate() == null){
                    throw new RuntimeException("이전 회차인 "+(repayRequestDto.getRound() - 1)+"회차 상환 정보를 찾을 수 없습니다.");
                }

                Long totalRepaymentAmount = beforeRepay.getTotalRepaymentAmount() + findRepay.getMonthlyRepaymentAmount();
                findRepay.setTotalRepaymentAmount(totalRepaymentAmount);

                balanceAmount = beforeRepay.getBalanceAmount() - findRepay.getMonthlyRepaymentAmount();
                findRepay.setBalanceAmount(balanceAmount);

            }
        }

        return findRepay;
    }

}
