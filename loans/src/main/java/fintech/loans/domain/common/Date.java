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

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Date {

    @CreatedDate
    @Column(updatable = false, columnDefinition = "DATETIME")
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime updateDate;

}
