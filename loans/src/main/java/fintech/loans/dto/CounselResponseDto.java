package fintech.loans.dto;

import fintech.loans.domain.common.Date;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CounselResponseDto {

    private String name;
    private LocalDateTime createDate;

}
