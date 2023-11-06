package fintech.loans.controller;

import fintech.loans.domain.Counsel;
import fintech.loans.dto.CounselRequestDto;
import fintech.loans.dto.CounselResponseDto;
import fintech.loans.dto.common.ResponseDto;
import fintech.loans.service.CounselServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor //construct 자동 생성
@RequestMapping("/loans")
public class CounselController {

    private final CounselServiceImpl counselService;

    /**
     * 상담 요청 저장
     */
    @PostMapping("/counsel")
    public ResponseDto<CounselResponseDto> counselSave(@RequestBody CounselRequestDto counselRequestDto){

        Counsel saveCounsel = counselService.save(counselRequestDto);
        CounselResponseDto counselResponseDto = CounselResponseDto
                .builder()
                .name(saveCounsel.getName())
                .createDate(saveCounsel.getCreateDate())
                .build();

        return ResponseDto.<CounselResponseDto>builder()
                .data(counselResponseDto)
                .build();
    }



}
