package org.nwolfub.messengerauth.api.outer;

import org.nwolfub.messengerauth.JsonBuilder;
import org.nwolfub.messengerauth.api.inner.TokenCommunicator;
import org.nwolfub.messengerauth.config.DatabaseConfigurator;
import org.nwolfub.messengerauth.database.UserDao;
import org.nwolfub.messengerauth.database.model.User;
import org.nwolfub.shared.LimitController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private static LimitController limitController = new LimitController();
    private static TokenCommunicator tokenCommunicator = new AnnotationConfigApplicationContext(DatabaseConfigurator.class).getBean()
    private static UserDao dao = new UserDao();

    @GetMapping
    public static ResponseEntity<String> login(@RequestParam(name = "username", defaultValue = "") String username, @RequestParam(name = "password", defaultValue = "") String password, @RequestHeader(value = "X-Forwarded-For", defaultValue = "0.0.0.0") String ip) {
        if(ip.equals("0.0.0.0")) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(JsonBuilder.loginResponse(false, "", "Direct connections are not allowed"));
        if(!username.equals("") && !password.equals("")) {
            if(limitController.isAllowed()) {
                try {
                    User u = (User) dao.get(username);
                    if(u.validatePassword(password)) {

                    }
                }
            }
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JsonBuilder.loginResponse(false, "", "Username and password cannot be empty"))
    }
}
