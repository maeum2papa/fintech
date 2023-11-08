package fintech.loans.service;

import fintech.loans.repository.CheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckServiceImpl implements CheckService{

    private final CheckRepository repository;

    @Override
    public void save() {
        repository.findAll();
    }
}
