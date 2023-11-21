package fintech.loans.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fintech.loans.domain.common.Date;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Repay extends Date {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checkerId")
    @JsonIgnore
    @ToString.Exclude
    private Checker checker;

    //회차
    private int round;

    //월상환원리금
    private Long monthlyRepaymentOfPrincipalAndInterest;

    //월상환원금
    private Long monthlyRepaymentAmount;

    //월상환이자
    private Long monthlyRepaymentInterest;

    //적용금리
    private Double interestRate;

    //납입원금계
    private Long totalRepaymentAmount;

    //잔금
    private Long balanceAmount;

    //상환일
    @Column(columnDefinition = "DATE")
    private LocalDate repaymentDate;


}
