package com.chalnakchalnak.search.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class DependencyRulesTest {
  @Test
  void innerLayersDoNotDependOnAdaptersOrSdkTypes() {
    var classes =
        new ClassFileImporter()
            .withImportOption(new ImportOption.DoNotIncludeTests())
            .importPackages("com.chalnakchalnak.search");
    noClasses()
        .that()
        .resideInAnyPackage("..application..", "..domain..")
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage(
            "..adapter..",
            "org.springframework.data..",
            "co.elastic..",
            "org.springframework.kafka..",
            "feign..")
        .check(classes);
    noClasses()
        .that()
        .resideInAPackage("..domain..")
        .should()
        .dependOnClassesThat()
        .resideInAPackage("..application..")
        .check(classes);
  }

  @Test
  void ownedTypesUseRoleNames() {
    var classes =
        new ClassFileImporter()
            .withImportOption(new ImportOption.DoNotIncludeTests())
            .importPackages("com.chalnakchalnak.search");
    for (String suffix : new String[] {"Dto", "Vo", "Impl", "Domain"}) {
      noClasses().should().haveSimpleNameEndingWith(suffix).check(classes);
    }
  }
}
