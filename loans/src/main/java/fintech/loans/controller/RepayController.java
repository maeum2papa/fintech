package fintech.loans.controller;

import fintech.loans.domain.Checker;
import fintech.loans.domain.Repay;
import fintech.loans.dto.RepayRequestDto;
import fintech.loans.dto.RepayResponseDto;
import fintech.loans.dto.common.ResponseDto;
import fintech.loans.service.CheckService;
import fintech.loans.service.RepayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/repay")
public class RepayController {

    private final CheckService checkService;
    private final RepayService repayService;

    @PostMapping("/manual")
    public ResponseDto<RepayResponseDto> repay(@RequestBody RepayRequestDto repayRequestDto){

        boolean result = false;

        Repay repay = repayService.setReapy(repayRequestDto);

        if(repay.getInterestRate() != null){
            result = true;
        }

        RepayResponseDto build = RepayResponseDto.builder()
                .checkId(repayRequestDto.getCheckId())
                .round(repayRequestDto.getRound())
                .result(result)
                .build();

        return ResponseDto.<RepayResponseDto>builder()
                .data(build)
                .build();
    }
}
