package fintech.loans.service;

import fintech.loans.domain.Counsel;
import fintech.loans.dto.CounselRequestDto;
import fintech.loans.exception.BasicException;
import fintech.loans.repository.CounselRepository;
import lombok.RequiredArgsConstructor;
import org.apache.el.stream.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CounselServiceImpl implements CounselService{

    private final CounselRepository counselRepository;

    @Override
    public Counsel save(CounselRequestDto counselRequestDto) {

        if(counselRequestDto.getName() == null || counselRequestDto.getName().isEmpty()){
            throw new BasicException("name은 필수 입니다.","301000");
        }

        if(counselRequestDto.getPhone() == null || counselRequestDto.getPhone().isEmpty()){
            throw new BasicException("phone은 필수 입니다.","302000");
        }

        Counsel counsel = new Counsel();
        counsel.setName(counselRequestDto.getName());
        counsel.setPhone(counselRequestDto.getPhone());
        counsel.setEmail(counselRequestDto.getEmail());
        counsel.setMemo(counselRequestDto.getMemo());

        System.out.println("counsel.toString() = " + counsel.toString());
        Counsel saveCounsel = counselRepository.save(counsel);
        System.out.println("saveCounsel.toString() = " + saveCounsel.toString());
        return saveCounsel;
    }

}
