package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

public abstract class User {

    private final String name;
    private final String mail;
    private final String password;

    public User(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }
}
