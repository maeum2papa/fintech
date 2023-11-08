package fintech.loans.controller;

import fintech.loans.service.CheckServiceImpl;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/check")
public class CheckController {

    private CheckServiceImpl checkService;

    @GetMapping("")
    public void registerLoan(){
        checkService.save();
    }


    /*
- 대출신청
- 대출심사
- 대출심사결과조회(제안)
- 대출계약
     */

}
