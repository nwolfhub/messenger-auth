package org.nwolfub.messengerauth;

import org.nwolfub.messengerauth.api.inner.TokenCommunicator;
import org.nwolfub.messengerauth.config.DatabaseConfigurator;
import org.nwolfub.messengerauth.database.UserDao;
import org.nwolfub.messengerauth.database.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class MessengerAuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(MessengerAuthApplication.class, args);
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfigurator.class);

	}

}
