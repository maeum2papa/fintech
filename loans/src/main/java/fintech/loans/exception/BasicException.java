package fintech.loans.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class BasicException extends RuntimeException {

    private String code;

    public BasicException(String message) {
        super(message);
        this.code = "999999";
    }

    public BasicException(String message, String code) {
        super(message);
        this.code = code;
    }
}
