package fintech.loans.service;

import fintech.loans.domain.Checker;
import fintech.loans.dto.CheckSaveRequestDto;

public interface CheckService {


    //대출신청
    public Checker saveCheckLoan(CheckSaveRequestDto checkSaveRequestDto);

    //대출심사
    public Checker checkLoan(Long id);

    //대출심사결과조회(제안)
    public Checker viewCheckLoan(Long id);

    //대출계약
    public Checker contractLoan();

}
