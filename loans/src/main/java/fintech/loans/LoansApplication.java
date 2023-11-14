package fintech.loans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing //@CreatedDate 및 @LastModifiedDate 을 사용하기 위한 어노테이션
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
		log.info("+++ WELCOME MAEUM2PAPA BANK LOANS +++");
	}

}
