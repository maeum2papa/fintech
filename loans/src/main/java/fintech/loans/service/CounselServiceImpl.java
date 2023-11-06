package fintech.loans.service;

import fintech.loans.domain.Counsel;
import fintech.loans.dto.CounselRequestDto;
import fintech.loans.repository.CounselRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CounselServiceImpl implements CounselService{

    private final CounselRepository counselRepository;

    @Override
    public Counsel save(CounselRequestDto counselRequestDto) {

        if(counselRequestDto.getName() == null || counselRequestDto.getName().isEmpty()){
            throw new NullPointerException("name은 필수 입니다.");
        }

        if(counselRequestDto.getPhone() == null || counselRequestDto.getPhone().isEmpty()){
            throw new NullPointerException("phone은 필수 입니다.");
        }

        if(counselRequestDto.getMemo() == null || counselRequestDto.getMemo().isEmpty()){
            throw new NullPointerException("memo는 필수 입니다.");
        }

        Counsel counsel = Counsel.builder()
                        .name(counselRequestDto.getName())
                        .phone(counselRequestDto.getPhone())
                        .email(counselRequestDto.getEmail())
                        .memo(counselRequestDto.getMemo())
                        .build();

        return counselRepository.save(counsel);
    }

}
