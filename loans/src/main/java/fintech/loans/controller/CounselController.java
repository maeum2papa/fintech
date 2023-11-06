package fintech.loans.controller;

import fintech.loans.domain.Counsel;
import fintech.loans.dto.CounselRequestDto;
import fintech.loans.dto.CounselResponseDto;
import fintech.loans.dto.common.ResponseDto;
import fintech.loans.service.CounselServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor //construct 자동 생성
public class CounselController {

    private final CounselServiceImpl counselService;

    @PostMapping("/loans/counsel")
    public ResponseDto<CounselResponseDto> counselSave(@RequestBody CounselRequestDto counselRequestDto){

        Counsel saveCounsel = counselService.save(counselRequestDto);
        CounselResponseDto counselResponseDto = new CounselResponseDto(saveCounsel.getName(), saveCounsel.getCreateDate());

        return ResponseDto.<CounselResponseDto>builder()
                .data(counselResponseDto)
                .build();
    }

}
