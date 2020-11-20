package ar.edu.unq.desapp.grupon022020.backenddesappapi.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@RunWith(ArchUnitRunner.class)
public class AppArchitectureTest {

    @Test
    public void testControllersAnnotations() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice");
        ArchRule myRule = classes()
                .that().haveSimpleNameEndingWith("Controller")
                .should().beAnnotatedWith(RestController.class);
        myRule.check(importedClasses);
    }

    @Test
    public void testServicesAnnotations() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ar.edu.unq.desapp.grupon022020.backenddesappapi.service");
        ArchRule myRule = classes()
                .that().haveSimpleNameEndingWith("Service")
                .should().beAnnotatedWith(Service.class);
        myRule.check(importedClasses);
    }

    @Test
    public void testRepositoriesAnnotations() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence");
        ArchRule myRule = classes()
                .that().haveSimpleNameEndingWith("Repository")
                .should().beAnnotatedWith(Repository.class);
        myRule.check(importedClasses);
    }
}
