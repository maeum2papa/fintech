package fintech.loans.controller;

import fintech.loans.domain.Checker;
import fintech.loans.dto.CheckResponseDto;
import fintech.loans.dto.CheckSaveRequestDto;
import fintech.loans.dto.common.ResponseDto;
import fintech.loans.service.CheckServiceImpl;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/check")
public class CheckController {

    private final CheckServiceImpl checkService;

    //대출심사신청
    @PostMapping()
    public ResponseDto<CheckResponseDto> registerLoan(@Valid @RequestBody CheckSaveRequestDto checkSaveRequestDto){

        Checker checker = checkService.saveCheckLoan(checkSaveRequestDto);

        CheckResponseDto build = CheckResponseDto.builder()
                .id(checker.getId())
                .createDate(checker.getCreateDate())
                .build();

        return ResponseDto.<CheckResponseDto>builder()
                .data(build)
                .build();
    }


}
