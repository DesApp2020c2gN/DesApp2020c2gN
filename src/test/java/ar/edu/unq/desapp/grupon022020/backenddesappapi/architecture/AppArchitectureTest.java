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
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

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

    @Test
    public void testControllersPackage() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ar.edu.unq.desapp.grupon022020.backenddesappapi");
        ArchRule myRule = classes()
                .that().haveSimpleNameEndingWith("Controller")
                .should().resideInAPackage("ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice");
        myRule.check(importedClasses);
    }

    @Test
    public void testServicesPackage() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ar.edu.unq.desapp.grupon022020.backenddesappapi");
        ArchRule myRule = classes()
                .that().haveSimpleNameEndingWith("Service")
                .should().resideInAPackage("ar.edu.unq.desapp.grupon022020.backenddesappapi.service");
        myRule.check(importedClasses);
    }

    @Test
    public void testRepositoriesPackage() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ar.edu.unq.desapp.grupon022020.backenddesappapi");
        ArchRule myRule = classes()
                .that().haveSimpleNameEndingWith("Repository")
                .should().resideInAPackage("ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence");
        myRule.check(importedClasses);
    }

    @Test
    public void testIndependentModelClasses() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ar.edu.unq.desapp.grupon022020.backenddesappapi");
        ArchRule myRule = noClasses()
                .that().resideInAPackage("..model..")
                .should().dependOnClassesThat().resideInAnyPackage("..webservice..", "..service..", "..backenddesappapi.persistence..", "..aspects..");
        myRule.check(importedClasses);
    }

    @Test
    public void testArchitectureLayers() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ar.edu.unq.desapp.grupon022020.backenddesappapi");
        ArchRule myRule = layeredArchitecture()
                .layer("Controller").definedBy("..webservice..")
                .layer("Service").definedBy("..service..")
                .layer("Persistence").definedBy("..persistence..")
                .layer("Aspect").definedBy("..aspects..")
                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Aspect")
                .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service");
        myRule.check(importedClasses);
    }

}
