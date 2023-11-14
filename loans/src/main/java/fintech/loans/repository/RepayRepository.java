package fintech.loans.repository;

import fintech.loans.domain.Repay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface RepayRepository extends JpaRepository<Repay,Long> {

    List<Repay> findAllByCheckerIdOrderByIdAsc(Long checkerId);
}
