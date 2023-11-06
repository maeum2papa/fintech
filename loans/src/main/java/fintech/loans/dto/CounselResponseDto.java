package fintech.loans.dto;

import fintech.loans.domain.common.Date;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CounselResponseDto {

    private String name;
    private LocalDateTime createDate;

}
