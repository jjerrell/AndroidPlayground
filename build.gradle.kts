// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.kotlin) apply false
    alias(libs.plugins.diffplug.spotless)
    id("org.jetbrains.dokka") version "1.9.0"
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")

    // configure all format tasks at once
    tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
        moduleName.set(project.name)
        moduleVersion.set(project.version.toString())
        failOnWarning.set(false)
        suppressObviousFunctions.set(true)
        suppressInheritedMembers.set(true)
        offlineMode.set(false)

        dokkaSourceSets.configureEach {
            suppress.set(false)
            displayName.set(name)
            documentedVisibilities.set(
                setOf(
                    org.jetbrains.dokka.DokkaConfiguration.Visibility.PUBLIC,
                    org.jetbrains.dokka.DokkaConfiguration.Visibility.PRIVATE,
                    org.jetbrains.dokka.DokkaConfiguration.Visibility.INTERNAL,
                )
            )
            reportUndocumented.set(true)
            skipEmptyPackages.set(true)
            skipDeprecated.set(false)
            suppressGeneratedFiles.set(true)
            jdkVersion.set(17)
        }
    }
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    ratchetFrom("origin/main")
    format("misc") {
        target("*.gradle", "*.md", ".gitignore")

        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }

    kotlin {
        target("**/*.kt")
        ktfmt().kotlinlangStyle().configure { it.setRemoveUnusedImport(true) }
        licenseHeader("/* (C) 2023 Jacob Jerrell */")
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        ktfmt().kotlinlangStyle().configure { it.setRemoveUnusedImport(true) }
    }
}
