package fintech.loans.service;

import fintech.loans.domain.Counsel;
import fintech.loans.dto.CounselRequestDto;
import fintech.loans.exception.BasicException;
import fintech.loans.repository.CounselRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CounselServiceImpl implements CounselService{

    private final CounselRepository counselRepository;

    @Override
    public Counsel save(CounselRequestDto counselRequestDto) {
        System.out.println("counselRequestDto = " + counselRequestDto.getClass());
        Counsel counsel = new Counsel(counselRequestDto.getName(),counselRequestDto.getPhone(),counselRequestDto.getEmail(),counselRequestDto.getMemo());
        Counsel saveCounsel = counselRepository.save(counsel);
        return saveCounsel;
    }

}
