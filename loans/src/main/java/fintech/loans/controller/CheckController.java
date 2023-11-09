package fintech.loans.controller;

import fintech.loans.domain.Checker;
import fintech.loans.dto.CheckResponseDto;
import fintech.loans.dto.CheckSaveRequestDto;
import fintech.loans.dto.common.ResponseDto;
import fintech.loans.dto.eum.InterestRateEnum;
import fintech.loans.dto.eum.LoanKindEnum;
import fintech.loans.dto.eum.StatusEnum;
import fintech.loans.service.CheckServiceImpl;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/check")
public class CheckController {

    private final CheckServiceImpl checkService;

    //대출심사신청
    @PostMapping()
    public ResponseDto<CheckResponseDto> registerLoan(@Valid @RequestBody CheckSaveRequestDto checkSaveRequestDto){

        log.info("registerLoan.checkSaveRequestDto = {}",checkSaveRequestDto.toString());

        Checker checker = checkService.saveCheckLoan(checkSaveRequestDto);

        CheckResponseDto build = CheckResponseDto.builder()
                .id(checker.getId())
                .createDate(checker.getCreateDate())
                .build();

        return ResponseDto.<CheckResponseDto>builder()
                .data(build)
                .build();
    }


    //대출심사
    @PutMapping("/{id}")
    public ResponseDto<CheckResponseDto> checkLoan(@Valid @PathVariable("id") Long id){

        Checker checker = checkService.checkLoan(id);

        CheckResponseDto build = CheckResponseDto.builder()
                .id(checker.getId())
                .name(checker.getName())
                .phone(checker.getPhone())
                .email(checker.getEmail())
                .address(checker.getAddress())
                .addressDetail(checker.getAddressDetail())
                .loanKind(checker.getLoanKind())
                .otherYearPrincipalAndInterrest(checker.getOtherYearPrincipalAndInterrest())
                .LTV(checker.getLTV())
                .DSR(checker.getDSR())
                .income(checker.getIncome())
                .asset(checker.getAsset())
                .amount(checker.getAmount())
                .loanRepaymentPeriod(checker.getLoanRepaymentPeriod())
                .interestRateKind(checker.getInterestRateKind())
                .interestRate(checker.getInterestRate())
                .monthlyRepaymentOfPrincipalAndInterest(checker.getMonthlyRepaymentAmount())
                .monthlyRepaymentAmount(checker.getMonthlyRepaymentAmount())
                .monthlyRepaymentInterest(checker.getMonthlyRepaymentInterest())
                .totalLoanInterest(checker.getTotalLoanInterest())
                .createDate(checker.getCreateDate())
                .examinationDate(checker.getExaminationDate())
                .status(checker.getStatus())
                .contractDate(checker.getContractDate())
                .contractEndDate(checker.getContractEndDate())
                .build();

        return ResponseDto.<CheckResponseDto>builder()
                .data(build)
                .build();
    }

    //대출심사결과 조회
    @PostMapping("/{id}")
    public ResponseDto<CheckResponseDto> checkLoanResult(@Valid @PathVariable("id") Long id) {

        Checker checker = checkService.viewCheckLoan(id);

        CheckResponseDto build = CheckResponseDto.builder()
                .id(checker.getId())
                .name(checker.getName())
                .phone(checker.getPhone())
                .email(checker.getEmail())
                .address(checker.getAddress())
                .addressDetail(checker.getAddressDetail())
                .loanKind(checker.getLoanKind())
                .otherYearPrincipalAndInterrest(checker.getOtherYearPrincipalAndInterrest())
                .LTV(checker.getLTV())
                .DSR(checker.getDSR())
                .income(checker.getIncome())
                .asset(checker.getAsset())
                .amount(checker.getAmount())
                .loanRepaymentPeriod(checker.getLoanRepaymentPeriod())
                .interestRateKind(checker.getInterestRateKind())
                .interestRate(checker.getInterestRate())
                .monthlyRepaymentOfPrincipalAndInterest(checker.getMonthlyRepaymentAmount())
                .monthlyRepaymentAmount(checker.getMonthlyRepaymentAmount())
                .monthlyRepaymentInterest(checker.getMonthlyRepaymentInterest())
                .totalLoanInterest(checker.getTotalLoanInterest())
                .createDate(checker.getCreateDate())
                .examinationDate(checker.getExaminationDate())
                .status(checker.getStatus())
                .contractDate(checker.getContractDate())
                .contractEndDate(checker.getContractEndDate())
                .build();

        return ResponseDto.<CheckResponseDto>builder()
                .data(build)
                .build();
    }

}
