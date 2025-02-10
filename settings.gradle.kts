pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://www.jitpack.io")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://www.jitpack.io")
    }
    versionCatalogs {
        create("jetpack") {
            from(files("gradle/jetpack.versions.toml"))
        }
        create("testdebug") {
            from(files("gradle/testdebug.versions.toml"))
        }
    }
}

rootProject.name = "Bookshelf"
include(":app")
 