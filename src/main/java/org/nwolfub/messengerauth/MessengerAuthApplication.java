package org.nwolfub.messengerauth;

import org.nwolfub.messengerauth.database.UserDao;
import org.nwolfub.messengerauth.database.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class MessengerAuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(MessengerAuthApplication.class, args);
	}

}
