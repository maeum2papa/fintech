package fintech.loans.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL) // 값이 NULL이 아닌 경우에만 포함
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

    @Valid
    private T data;

    private List<String> errors;

}
