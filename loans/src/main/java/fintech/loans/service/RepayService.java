package fintech.loans.service;

import fintech.loans.domain.Checker;
import fintech.loans.domain.Repay;

import java.util.List;

public interface RepayService {

    public void createRepaySchedule(Long id);

    public List<Repay> getRepays(Long checkId);

}
