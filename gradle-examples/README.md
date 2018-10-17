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

## Developer Notes

### Updating the Gradle version.

Please update the Gradle version in [wrapper properties file](gradle/wrapper/gradle-wrapper.properties). The Gradle
wrappers in the projects have been edited to make use of a single Gradle wrapper installation to avoid bloat in the
repository.
