package fintech.loans.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL) // 값이 NULL이 아닌 경우에만 포함
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CounselResponseDto {

    private String name;
    private String phone;
    private String email;
    private String memo;
    private LocalDateTime counselDate;
    private LocalDateTime createDate;

}
