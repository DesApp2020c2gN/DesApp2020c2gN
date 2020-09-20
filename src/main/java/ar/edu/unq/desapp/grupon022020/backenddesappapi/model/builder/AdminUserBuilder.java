package ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.AdminUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.System;

public class AdminUserBuilder {

    private String name = "default_name";
    private String mail = "default_mail";
    private String password = "default_password";
    private System system = SystemBuilder.aSystem().build();

    public static AdminUserBuilder anAdminUser() {
        return new AdminUserBuilder();
    }

    public AdminUser build() {
        AdminUser newAdminUser = new AdminUser(name, mail, password, system);
        return newAdminUser;
    }

    public AdminUserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public AdminUserBuilder withMail(String mail) {
        this.mail = mail;
        return this;
    }

    public AdminUserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public AdminUserBuilder withSystem(System system) {
        this.system = system;
        return this;
    }
}
