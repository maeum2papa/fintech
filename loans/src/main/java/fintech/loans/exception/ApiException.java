package fintech.loans.exception;

import fintech.loans.dto.common.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ApiException{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ResponseDto> ApiException(
            MethodArgumentNotValidException e,
            Exception be

    ) {

        log.error("{}",e.toString());

        List<String> errors = e.getFieldErrors()
                .stream()
                .map(i -> {
                    return String.format("%s : %s은 %s", i.getField(), i.getRejectedValue(), i.getDefaultMessage());
                })
                .collect(Collectors.toList());

        ResponseDto<Object> build = ResponseDto
                .builder()
                .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message(errors.get(0))
                .errors(errors)
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(build);
    }
}
