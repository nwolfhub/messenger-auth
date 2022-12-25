package org.nwolfhub.messengerauth.api.outer;

import org.nwolfhub.messengerauth.JsonBuilder;
import org.nwolfhub.messengerauth.api.inner.TokenCommunicator;
import org.nwolfhub.messengerauth.config.DatabaseConfigurator;
import org.nwolfhub.shared.database.UserDao;
import org.nwolfhub.shared.database.model.User;
import org.nwolfhub.shared.LimitController;
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
    private static TokenCommunicator tokenCommunicator = new AnnotationConfigApplicationContext(DatabaseConfigurator.class).getBean(TokenCommunicator.class);
    private static UserDao dao;
    static {
        dao = (UserDao) new AnnotationConfigApplicationContext(DatabaseConfigurator.class).getBean("usersDao");
    }
    

    @GetMapping("/auth/login")
    public static ResponseEntity<String> login(@RequestParam(name = "username", defaultValue = "") String username, @RequestParam(name = "password", defaultValue = "") String password, @RequestHeader(value = "X-Forwarded-For", defaultValue = "0.0.0.0") String ip) {
        if(ip.equals("0.0.0.0")) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(JsonBuilder.loginResponse(false, "", "Direct connections are not allowed"));
        if(limitController.isAllowed(ip)) {
            if (!username.equals("") && !password.equals("")) {
                try {
                    User u;
                    if ((u = (User) dao.get(username)) != null) {
                        if (u.validatePassword(password)) {
                            return ResponseEntity.status(HttpStatus.OK).body(JsonBuilder.loginResponse(true, tokenCommunicator.makeToken(u.getId()), ""));
                        } else {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JsonBuilder.loginResponse(false, "", "Wrong password"));
                        }
                    } else
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JsonBuilder.loginResponse(false, "", "User " + username + " does not exist"));
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JsonBuilder.loginResponse(false, "", "Server side error occurred: " + e));
                }
            } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JsonBuilder.loginResponse(false, "", "Username and password cannot be empty"));
        } else return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(JsonBuilder.loginResponse(false, "", "Too many requests from this IP"));
    }

    @GetMapping("/auth/register")
    public static ResponseEntity<String> register(@RequestParam(name = "username", defaultValue = "") String username, @RequestParam(name = "password", defaultValue = "") String password, @RequestHeader(value = "X-Forwarded-For", defaultValue = "0.0.0.0") String ip) {
        if(ip.equals("0.0.0.0")) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(JsonBuilder.loginResponse(false, "", "Direct connections are not allowed"));
        try {
            if (limitController.isAllowed(ip)) {
                if (username.length() >= 5) {
                    if (password.length() >= 6) {
                        User u;
                        if ((u = (User) dao.get(username)) == null) {
                            u = new User(username, password);
                            dao.save(u);
                            return ResponseEntity.status(HttpStatus.OK).body(JsonBuilder.registerResponse(true, ""));
                        } else
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JsonBuilder.registerResponse(false, "User " + username + " exists"));
                    } else
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JsonBuilder.registerResponse(false, "Password should be at least 6 symbols long"));
                } else
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JsonBuilder.registerResponse(false, "Username should be at least 5 symbols long"));
            } else return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(JsonBuilder.registerResponse(false, "Too many requests from this IP"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JsonBuilder.registerResponse(false, "Server error occurred: " + e));
        }
    }

}
