package fintech.loans.exception;

import fintech.loans.dto.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class ApiException extends RuntimeException{

    @ExceptionHandler(BasicException.class)
    protected ExceptionDto ApiException(BasicException e, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return new ExceptionDto(e.getMessage());
    }
}
