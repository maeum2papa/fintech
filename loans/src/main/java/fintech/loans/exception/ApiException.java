package fintech.loans.exception;

import fintech.loans.dto.common.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class ApiException{

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseDto> ApiException(
            Exception e
    ) {

        log.error("{}",e);

        ResponseDto<Object> build = ResponseDto
                .builder()
                .statusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .message(e.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(build);
    }
}
