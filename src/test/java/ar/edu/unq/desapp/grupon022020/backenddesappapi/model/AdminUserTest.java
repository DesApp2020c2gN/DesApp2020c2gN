package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminUserTest {

    @Test
    public void testAdminUserName() {
        String name = "Admin";
        AdminUser adminUser = AdminUserBuilder.anAdminUser().withName(name).build();

        assertEquals(adminUser.getName(), name);
    }

}