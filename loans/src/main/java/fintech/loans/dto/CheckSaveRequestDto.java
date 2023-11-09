package fintech.loans.dto;

import fintech.loans.dto.eum.InterestRateEnum;
import fintech.loans.dto.eum.LoanKindEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import javax.persistence.PrePersist;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckSaveRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String addressDetail;

    @NotNull
    private LoanKindEnum loanKind;

    //타대출연원리금
    @NotNull
    private Long otherYearPrincipalAndInterrest;

    //(대출금액/담보가치) < LTV = true;
    private Double LTV;

    //(연소득/(타대출연원리금 + (월상환원리금 *12)))) < DSR = true;
    private Double DSR;

    //소득
    @NotNull
    private Long income;

    //자산
    @NotNull
    private Long asset;

    //대출희망금액
    @NotNull
    private Long amount;

    //대출상환기간(개월)
    @NotNull
    private Integer loanRepaymentPeriod;

    //대출금리종류
    @NotNull
    private InterestRateEnum interestRateKind;

    public void setLoanKind(LoanKindEnum loanKind){
        this.loanKind = loanKind;

        if(this.loanKind == LoanKindEnum.HOUSE){
            if(this.LTV == null){
                this.LTV = 0d;
            }

            if(this.DSR == null){
                this.DSR = 0d;
            }
        }
    }
}
