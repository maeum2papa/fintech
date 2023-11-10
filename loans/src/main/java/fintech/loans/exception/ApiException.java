package fintech.loans.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import fintech.loans.dto.common.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.NotNullExpression;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ApiException{

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<ResponseDto> ApiException(
            MethodArgumentNotValidException e
    ) {
        log.error("",e);

        List<String> errors = e.getFieldErrors()
                .stream()
                .map(i -> {
                    return String.format("%s : %s 은 %s", i.getField(), i.getRejectedValue(), i.getDefaultMessage());
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


    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ResponseDto> ApiException(
            HttpMessageNotReadableException e
    ){

        String message = "JSON 형식이 잘못되었습니다.";

        log.error(message);


        ResponseDto<Object> build = ResponseDto
                .builder()
                .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message(message)
                .errors(List.of(message))
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(build);

    }


    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ResponseDto> ApiException(
            NullPointerException e
    ) {

        log.error("",e);


        ResponseDto<Object> build = ResponseDto
                .builder()
                .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message("데이터를 찾을 수 없습니다.")
                .errors(List.of("데이터를 찾을 수 없습니다."))
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(build);
    }


    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ResponseDto> ApiException(
            RuntimeException e
    ) {

        log.error("",e);

        ResponseDto<Object> build = ResponseDto
                .builder()
                .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message(e.getMessage())
                .errors(List.of(e.getMessage()))
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(build);
    }
}
