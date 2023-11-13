package fintech.loans.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import fintech.loans.domain.Repay;
import fintech.loans.dto.eum.InterestRateEnum;
import fintech.loans.dto.eum.LoanKindEnum;
import fintech.loans.dto.eum.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@JsonInclude(Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckResponseDto {

    private Long id;

    private String name;

    private String phone;

    private String email;

    private String address;

    private String addressDetail;

    private LoanKindEnum loanKind;

    //타대출연원리금
    private Long otherYearPrincipalAndInterrest;

    //(대출금액/담보가치) < LTV = true;
    private Double LTV;

    //(연소득/(타대출연원리금 + (월상환원리금 *12)))) < DSR = true;
    private Double DSR;

    //소득
    private Long income;

    //자산
    private Long asset;

    //대출희망금액
    private Long amount;

    //대출상환기간(개월)
    private Integer loanRepaymentPeriod;

    //대출금리종류
    private InterestRateEnum interestRateKind;

    //금리
    private Double interestRate;

    //월상환원리금
    private Long monthlyRepaymentOfPrincipalAndInterest;

    //월상환원금
    private Long monthlyRepaymentAmount;

    //월상환이자
    private Long monthlyRepaymentInterest;

    //총대출이자
    private Long totalLoanInterest;

    //심사신청일
    //Date.createDate

    //심사일
    private LocalDateTime examinationDate;

    //상태
    private StatusEnum status;

    //계약일
    private LocalDateTime contractDate;

    //계약종료일
    private LocalDateTime contractEndDate;

    private LocalDateTime createDate;

    private List<Repay> repay;
}
