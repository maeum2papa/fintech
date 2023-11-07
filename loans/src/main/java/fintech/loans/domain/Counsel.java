package fintech.loans.domain;

import com.sun.istack.NotNull;
import fintech.loans.domain.common.Date;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Counsel extends Date{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "counselId")
    private Long Id;

    private String name;

    @Column(columnDefinition = "VARCHAR(20)")
    private String phone;

    private String email = "";

    @Column(columnDefinition = "TEXT")
    private String memo = "";

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime counselDate;

}
