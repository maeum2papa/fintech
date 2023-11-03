package fintech.loans.repository;

import fintech.loans.domain.Counsel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounselRepository extends JpaRepository<Counsel,Long> {
    
}
