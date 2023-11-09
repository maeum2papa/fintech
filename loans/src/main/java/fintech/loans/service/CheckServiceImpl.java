package fintech.loans.service;

import fintech.loans.domain.Checker;
import fintech.loans.dto.CheckSaveRequestDto;
import fintech.loans.dto.eum.InterestRateEnum;
import fintech.loans.dto.eum.LoanKindEnum;
import fintech.loans.dto.eum.StatusEnum;
import fintech.loans.repository.CheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Checker checkLoan() {
        return null;
    }

    @Override
    public Checker viewCheckLoan() {
        return null;
    }

    @Override
    public Checker contractLoan() {
        return null;
    }
}
