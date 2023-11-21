package fintech.loans.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepayRequestDto {

    @NotBlank
    private Long checkId;

    @NotBlank
    private Integer round;

    @NotBlank
    private Long monthlyRepayment;

}
