package fintech.loans.domain.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@JsonInclude(Include.NON_NULL) // 값이 NULL이 아닌 경우에만 포함
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) //@CreatedDate 및 @LastModifiedDate 필드에 자동으로 생성 및 수정 일자를 설정
public class Date {

    @CreatedDate
    @Column(updatable = false, columnDefinition = "DATETIME")
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime updateDate;

}
