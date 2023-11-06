package fintech.loans.dto;

import fintech.loans.domain.common.Date;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CounselRequestDto {

    private String name;
    private String phone;
    private String email;
    private String memo;
}
