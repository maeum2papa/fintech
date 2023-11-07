package fintech.loans.controller;

import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import fintech.loans.domain.Counsel;
import fintech.loans.dto.CounselRequestDto;
import fintech.loans.dto.CounselResponseDto;
import fintech.loans.dto.common.ResponseDto;
import fintech.loans.service.CounselServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
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
    public ResponseDto<CounselResponseDto> counselSave(@Valid @RequestBody CounselRequestDto counselRequestDto){

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


    /**
     * 상담조회
     */
    @GetMapping("/counsel/{id}")
    public ResponseDto<CounselResponseDto> counselVeiw(@Valid @PathVariable("id") Long counselId){

        Counsel findCounsel = counselService.findById(counselId);
        CounselResponseDto counselResponseDto = CounselResponseDto
                .builder()
                .name(findCounsel.getName())
                .phone(findCounsel.getPhone())
                .email(findCounsel.getEmail())
                .memo(findCounsel.getMemo())
                .counselDate(findCounsel.getCounselDate())
                .createDate(findCounsel.getCreateDate())
                .build();

        return ResponseDto.<CounselResponseDto>builder()
                .data(counselResponseDto)
                .build();
    }

    /**
     * 상담완료 : counselDate 생성
     */
    @GetMapping("/counsel/{id}/update")
    public ResponseDto<CounselResponseDto> successCounsel(@Valid @PathVariable("id") Long counselId){

        Counsel findCounsel = counselService.findById(counselId);
        findCounsel.setCounselDate(LocalDateTime.now());

        CounselResponseDto counselResponseDto = CounselResponseDto
                .builder()
                .name(findCounsel.getName())
                .phone(findCounsel.getPhone())
                .email(findCounsel.getEmail())
                .memo(findCounsel.getMemo())
                .counselDate(findCounsel.getCounselDate())
                .createDate(findCounsel.getCreateDate())
                .build();

        return ResponseDto.<CounselResponseDto>builder()
                .data(counselResponseDto)
                .build();
    }
}
