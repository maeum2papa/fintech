package fintech.loans.dto;

import com.sun.istack.NotNull;
import fintech.loans.domain.common.Date;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.validation.constraints.NotEmpty;
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
