package fintech.loans.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExceptionDto {

    private String code;
    private String message;
    private LocalDateTime timeStamp = LocalDateTime.now();

    public ExceptionDto(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
