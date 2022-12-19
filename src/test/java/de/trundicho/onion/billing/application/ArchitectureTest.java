package de.trundicho.onion.billing.application;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

class ArchitectureTest {

    private static final String BASE_PACKAGE = "de.trundicho.onion.billing";
    private final JavaClasses classes = new ClassFileImporter().withImportOption(new ImportOption.DoNotIncludeTests())
                                                               .importPackages(BASE_PACKAGE);

    @Test
    void testOnionArchitecture() {
        onionArchitecture().domainModels(BASE_PACKAGE + "..domain..")
                           .domainServices(BASE_PACKAGE + "..service..")
                           .applicationServices(BASE_PACKAGE + "..application..")
                           .adapter("filepersistence", BASE_PACKAGE + "..infrastructure.filepersistence..")
                           .adapter("backend", BASE_PACKAGE + "..infrastructure.backend..")
                           .adapter("database", BASE_PACKAGE + "..infrastructure.database..")
                           .adapter("web", BASE_PACKAGE + "..web..")
                           .check(classes);
    }

    @Test
    void testAllowedDomainDependencies() {
        String domainPackage = BASE_PACKAGE + ".domain..";
        ArchRule r1 = classes().that()
                               .resideInAPackage(domainPackage)
                               .should()
                               .onlyDependOnClassesThat()
                               .resideInAnyPackage(domainPackage, "java..");
        r1.check(classes);
    }
}
