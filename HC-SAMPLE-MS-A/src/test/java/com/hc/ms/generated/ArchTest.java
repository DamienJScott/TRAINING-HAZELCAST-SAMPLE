package com.hc.ms.generated;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.hc.ms.generated");

        noClasses()
            .that()
                .resideInAnyPackage("com.hc.ms.generated.service..")
            .or()
                .resideInAnyPackage("com.hc.ms.generated.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.hc.ms.generated.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
