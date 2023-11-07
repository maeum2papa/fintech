package fintech.loans.service;

import fintech.loans.domain.Counsel;
import fintech.loans.dto.CounselRequestDto;
import fintech.loans.repository.CounselRepository;

public interface CounselService {

    /**
     * 상담요청신청 저장
     * @return
     */
    public Counsel save(CounselRequestDto counselRequestDto);

    public Counsel successCounsel(Long conselId);

    public Counsel findById(Long counselId);


}
