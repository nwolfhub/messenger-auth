package org.nwolfhub.shared.database.model;

import org.nwolfhub.shared.Utils;

import javax.persistence.*;
import javax.xml.bind.DatatypeConverter;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@Table(name = "users", schema = "users")
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
    @SequenceGenerator(name = "uidGen", sequenceName = "users.id_increaser", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uidGen")
    public Integer id;

    public String username;
    public String password;
    public String salt1;
    public String salt2;
    public boolean banned;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        try {
            this.salt1 = Utils.generateString(30);
            this.salt2 = Utils.generateString(30);
            String prePasswd = salt1 + password + salt2;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            this.password = DatatypeConverter.printBase64Binary(digest.digest(prePasswd.getBytes()));
            this.banned = false;
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

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) throws NoSuchAlgorithmException {
        String prePasswd = salt1 + password + salt2;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        this.password = DatatypeConverter.printBase64Binary(digest.digest(prePasswd.getBytes()));
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

    public boolean validatePassword(String password) {
        String prePasswd = salt1 + password + salt2;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return DatatypeConverter.printBase64Binary(digest.digest(prePasswd.getBytes())).equals(this.password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
