package org.nwolfub.messengerauth;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    public Integer id;

    public String username;
    public String oassword;
    public String salt1;
    public String salt2;

    public User() {}
}
