package fintech.loans.controller;

import fintech.loans.domain.Checker;
import fintech.loans.domain.Repay;
import fintech.loans.dto.CheckResponseDto;
import fintech.loans.dto.CheckSaveRequestDto;
import fintech.loans.dto.common.ResponseDto;
import fintech.loans.dto.eum.StatusEnum;
import fintech.loans.service.CheckService;
import fintech.loans.service.CheckServiceImpl;
import fintech.loans.service.RepayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/check")
public class CheckController {

    private final CheckServiceImpl checkService;
    private final RepayService repayService;

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
                .examinationDate(checker.getExaminationDate())
                .status(checker.getStatus())
                .build();

        return ResponseDto.<CheckResponseDto>builder()
                .data(build)
                .build();
    }

    //대출심사결과 조회
    @PostMapping("/{id}")
    public ResponseDto<CheckResponseDto> checkLoanResult(@Valid @PathVariable("id") Long id) {

        Checker checker = checkService.viewCheckLoan(id);
        List<Repay> repays = repayService.getRepays(checker.getId());

        log.info("checker = {}",checker);

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
                .monthlyRepaymentOfPrincipalAndInterest(checker.getMonthlyRepaymentOfPrincipalAndInterest())
                .totalLoanInterest(checker.getTotalLoanInterest())
                .createDate(checker.getCreateDate())
                .examinationDate(checker.getExaminationDate())
                .status(checker.getStatus())
                .contractDate(checker.getContractDate())
                .contractEndDate(checker.getContractEndDate())
                .repay(repays)
                .build();

        return ResponseDto.<CheckResponseDto>builder()
                .data(build)
                .build();
    }

    @PutMapping("/{id}/contract")
    public ResponseDto<CheckResponseDto> contractLoan(@PathVariable("id") Long id){

        Checker findChecker = checkService.viewCheckLoan(id);

        log.info("findChecker = {}",findChecker);

        if(findChecker == null){
            throw new RuntimeException("대출 신청 데이터가 없습니다.");
        }


        //계약
        Checker contractChecker = checkService.contractLoan(id);

        if(
            !(findChecker.getStatus() == StatusEnum.APPROVED &&
            Objects.equals(findChecker.getContractDate().toLocalDate(), LocalDate.now()))
        ){
            throw new RuntimeException("대출 계약중 문제가 발생했습니다.");
        }

        //총이자계산 및 회차별 상환 테이블
        Checker checker = checkService.equalRepaymentOfPrincipalAndInterest(contractChecker);

        //상환 스케줄 생성
        repayService.createRepaySchedule(checker);

        CheckResponseDto build = CheckResponseDto.builder()
                .id(checker.getId())
                .contractDate(checker.getContractDate())
                .build();

        return ResponseDto.<CheckResponseDto>builder()
                .data(build)
                .build();

    }

}
