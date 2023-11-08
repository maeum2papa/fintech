package fintech.loans.repository;

import fintech.loans.domain.Checker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckRepository extends JpaRepository<Checker, Long> {
}
