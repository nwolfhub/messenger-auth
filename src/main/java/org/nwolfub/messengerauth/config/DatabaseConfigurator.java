package org.nwolfub.messengerauth.config;

import org.nwolfub.messengerauth.Utils;
import org.nwolfub.messengerauth.database.HibernateController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

@Configuration
@ComponentScan(basePackageClasses = HibernateController.class)
public class DatabaseConfigurator {
    @Bean(name = "hibernate")
    public Properties getHibernateProperties() {
        String url = "";
        String username = "";
        String password = "";
        try (FileInputStream in = new FileInputStream("dbinfo.cfg")) {
            String input = new String(in.readAllBytes());
            HashMap<String, String> values = Utils.parseValues(input, "\n");
            url = values.get("url");
            username = values.get("username");
            password = values.get("password");
        } catch (FileNotFoundException e) {
            File f = new File("dbinfo.cfg");
            try {
                f.createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Properties prop = new Properties();
        prop.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        prop.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        prop.put("hibernate.connection.url", url);
        prop.put("hibernate.connection.username", username);
        prop.put("hibernate.connection.password", password);
        prop.put("hibernate.current_session_context_class", "thread");
        prop.put("hibernate.connection.CharSet", "utf8");
        prop.put("hibernate.connection.characterEncoding", "utf8");
        prop.put("hibernate.connection.useUnicode", true);
        prop.put("hibernate.connection.pool_size", 100);
        return prop;
    }
}
