package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
public abstract class User {

    //TODO: if AdminUser is deleted, this class is not required!

    @Column
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Column
    @Email
    @NotBlank(message = "Mail cannot be blank")
    private String mail;

    @Column
    @NotBlank(message = "Password cannot be blank")
    private String password;
    //TODO: save password as Hash in the database!

    public User() {	}

    public User(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
