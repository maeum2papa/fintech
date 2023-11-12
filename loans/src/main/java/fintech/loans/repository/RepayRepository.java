package fintech.loans.repository;

import fintech.loans.domain.Repay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepayRepository extends JpaRepository<Repay,Long> {
}
