package fintech.loans.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BasicException extends RuntimeException {

    private String code = "";
    private String desc = "";
    private String extraMessage = "";

//    public BasicException(ResultType resultType) {
//        super(resultType.getDesc());
//        this.code = resultType.getCode();
//        this.desc = resultType.getDesc();
//    }
//
//    public BasicException(ResultType resultType, String extraMessage) {
//        super(resultType.getDesc() + " - " + extraMessage);
//        this.code = resultType.getCode();
//        this.desc = resultType.getDesc();
//        this.extraMessage = extraMessage;
//    }
}
