package fintech.loans.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CounselResponseDto {

    private String name;
    private LocalDateTime createDate;

}
