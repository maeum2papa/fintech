package fintech.loans.service;


import fintech.loans.domain.Checker;
import fintech.loans.domain.Repay;
import fintech.loans.repository.CheckRepository;
import fintech.loans.repository.CounselRepository;
import fintech.loans.repository.RepayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RepayServiceImpl implements RepayService{

    private final RepayRepository repayRepository;
    private final CheckRepository checkRepository;

    @Override
    public void createRepaySchedule(Long checkerId) {

        Optional<Checker> findChecker = checkRepository.findById(checkerId);

        Checker checker = findChecker.orElse(null);

        if(checker != null){

            Integer loanRepaymentPeriod = checker.getLoanRepaymentPeriod();
            LocalDate repaymentDate = LocalDate.now();

            for (int i = 1; i < loanRepaymentPeriod+1; i++) {

                Repay repay = Repay.builder()
                        .round(i)
                        .checker(checker)
                        .repaymentDate(repaymentDate.plusMonths(i))
                        .build();

                repayRepository.save(repay);
            }

        }else{
            throw new RuntimeException("상환 스케줄 생성 오류가 발생하였습니다.");
        }

    }

    @Override
    public List<Repay> getRepays(Long checkId){
        return repayRepository.findAllByCheckerIdOrderByIdAsc(checkId);
    }

}
