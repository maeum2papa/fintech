package fintech.loans.service;

import fintech.loans.domain.Checker;
import fintech.loans.domain.Repay;
import fintech.loans.dto.RepayRequestDto;

import java.util.List;

public interface RepayService {

    public void createRepaySchedule(Checker checker);

    public List<Repay> getRepays(Long checkId);

    public Repay setReapy(RepayRequestDto repayRequestDto);

}
