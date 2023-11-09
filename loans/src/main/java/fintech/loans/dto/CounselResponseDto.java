package fintech.loans.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@JsonInclude(Include.NON_NULL) // 값이 NULL이 아닌 경우에만 포함
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CounselResponseDto {

    private Long counselId;
    private String name;
    private String phone;
    private String email;
    private String memo;
    private String adminMemo;
    private LocalDateTime counselDate;
    private LocalDateTime createDate;

}
