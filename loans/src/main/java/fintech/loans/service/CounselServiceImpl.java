package fintech.loans.service;

import fintech.loans.domain.Counsel;
import fintech.loans.dto.CounselRequestDto;
import fintech.loans.repository.CounselRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

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



    @Override
    public Counsel findById(Long counselId){
        Optional<Counsel> findCounsel = counselRepository.findById(counselId);
        return findCounsel.orElse(null);
    }


    @Override
    public Counsel successCounsel(Long conselId){

        Counsel findCounsel = findById(conselId);
        findCounsel.setCounselDate(LocalDateTime.now());

        return findCounsel;
    }

}
