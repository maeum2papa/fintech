package fintech.loans.domain;

import com.sun.istack.NotNull;
import fintech.loans.domain.common.Date;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Counsel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "counselId")
    private Long Id;

    private String name;

    @Column(columnDefinition = "VARCHAR(20)")
    private String phone;

    private String email;

    @Column(columnDefinition = "TEXT")
    private String memo;

    private LocalDateTime counselDate;

    @Embedded //공통필드 선언 후 사용시 이용
    private Date date;


    public Counsel(String name, String phone, String email, String memo) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.memo = memo;
    }
}
