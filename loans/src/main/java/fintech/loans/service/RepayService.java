package fintech.loans.service;

import fintech.loans.domain.Checker;
import fintech.loans.domain.Repay;

import java.util.ArrayList;
import java.util.List;

public interface RepayService {

    public void createRepaySchedule(Checker checker);

    public List<Repay> getRepays(Long checkId);

}
