package org.nwolfub.messengerauth.database.model;

import org.nwolfub.messengerauth.Utils;

import javax.persistence.*;
import javax.xml.bind.DatatypeConverter;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@Table(name = "users")
public class User implements Serializable {

    public static MessageDigest digest;

    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Id
    @SequenceGenerator(name = "uidGen", sequenceName = "id_increaser", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uidGen")
    public Integer id;

    public String username;
    public String password;
    public String salt1;
    public String salt2;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        try {
            this.salt1 = Utils.generateString(30);
            this.salt2 = Utils.generateString(30);
            String prePasswd = salt1 + password + salt2;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            this.password = DatatypeConverter.printBase64Binary(digest.digest(prePasswd.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    private String getPassword() {
        return password;
    }

    private User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSalt1() {
        return salt1;
    }

    public User setSalt1(String salt1) {
        this.salt1 = salt1;
        return this;
    }

    public String getSalt2() {
        return salt2;
    }

    public User setSalt2(String salt2) {
        this.salt2 = salt2;
        return this;
    }
}
