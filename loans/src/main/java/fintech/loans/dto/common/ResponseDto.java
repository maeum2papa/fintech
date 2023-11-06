package fintech.loans.dto.common;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {

    @Builder.Default
    private String statusCode = String.valueOf(HttpStatus.OK.value());

    @Builder.Default
    private String message = HttpStatus.OK.getReasonPhrase();

    @Builder.Default
    private LocalDateTime timeStamp = LocalDateTime.now();

    private T data;

}
