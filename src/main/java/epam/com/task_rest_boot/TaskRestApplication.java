package epam.com.task_rest_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class TaskRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskRestApplication.class, args);
	}

}
