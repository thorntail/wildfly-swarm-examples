# Thorntail Examples built using Gradle
This folder contains Thorntail example projects built using Gradle.

> NOTE: The Thorntail plugin for Gradle is not officially supported. YMMV.

## Single module projects
The following are examples of simple Gradle projects that leverage the [Gradle Application plugin](https://docs.gradle.org/current/userguide/application_plugin.html).
 
* [JAXRS + CDI](jaxrs-cdi)
* [EJB + JAXRS + Mail](ejb-jaxrs-mail)

## Multi-module Projects
The following are examples of multi-module Gradle projects that showcase building a traditional web application.

* [CDI + JAXRS + Swagger](multi-module)

> NOTE: The multi-module example showcases leveraging the upcoming Gradle 5 preview-feature of importing dependency
>  constraints from Maven BOMs, i.e., 
> [IMPROVED_POM_SUPPORT](https://docs.gradle.org/current/userguide/managing_transitive_dependencies.html#sec:bom_import)

## Generating Code Coverage for Arquillian Tests
Generating code coverage for Arquillian based tests can be tricky. The below instructions (also implemented in the
"multi-module" example) will help you setup JaCoCo for your Arquillian tests.

1. Configure the key configurations in your arquillian.xml file. [Example](multi-module/library-module/src/test/resources/arquillian.xml).
```
    <arquillian xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns="http://jboss.org/schema/arquillian"
                xsi:schemaLocation="http://jboss.org/schema/arquillian http://jboss.org/schema/arquillian/arquillian_1_0.xsd">
    
        <container qualifier="daemon" default="true">
            <configuration>
                ...
                <property name="javaVmArguments">${thorntail.arquillian.jvm.args:}</property>
                ...
            </configuration>
        </container>
        <extension qualifier="jacoco">
            <property name="includes">org.*</property>
        </extension>
    </arquillian>
```

2. Update your `build.gradle` file with the below changes. [Example](multi-module/build.gradle).
```
    dependencies {
        // Include the Arquillian JaCoCo extension
        testImplementation 'org.jboss.arquillian.extension:arquillian-jacoco:1.0.0.Alpha9'
    }
    ...
    test {
        ...
        doFirst {
            // Gradle starts the test task with the JaCoCo agent configured.
            // The Arquillian tests are run in a separate thread which does not have the JaCoCo agent enabled.
            // In the below code, we retrieve the JaCoCo details and make it available as a system property that is
            // consumed by the "arquillian.xml" file in the "test/resources" folder.
            String arg = jacoco.asJvmArg
            String dir = project.buildDir
            arg = arg.replaceAll(project.relativePath(dir), dir)
            systemProperty 'thorntail.arquillian.jvm.args', arg
        }
    }
```

## Developer Notes

### Updating the Gradle version.

Please update the Gradle version in [wrapper properties file](gradle/wrapper/gradle-wrapper.properties). The Gradle
wrappers in the projects have been edited to make use of a single Gradle wrapper installation to avoid bloat in the
repository.
