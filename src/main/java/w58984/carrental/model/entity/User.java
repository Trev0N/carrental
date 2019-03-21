package w58984.carrental.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {



    private String email;

    private String passwordHash;

    private String token;

    private boolean active;

    private boolean deleted;


}
