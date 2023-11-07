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
        Counsel counsel = Counsel.builder()
                        .name(counselRequestDto.getName())
                        .phone(counselRequestDto.getPhone())
                        .email(counselRequestDto.getEmail())
                        .memo(counselRequestDto.getMemo())
                        .build();

        return counselRepository.save(counsel);
    }

}
