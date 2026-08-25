plugins {
    `java-library`
    `maven-publish`
    idea
    id("net.neoforged.moddev")
    id("io.freefair.lombok")
    id("neoforge-mutex")
}

val modVersion = property("mod_version") as String
// Version scheme follows upstream convention: 1.1.0+<build|pr>.<N> (Minecraft target is conveyed by the jar name).
// The mc_index property makes N unique per Minecraft version when a CI run builds all of them at once.
val ciRun = System.getenv("CI_BUILD") != "false"
val buildType = if (ciRun && System.getenv("PR_BUILD") != "false") "pr" else "build"
val runNumber = System.getenv("GITHUB_RUN_NUMBER")?.toIntOrNull()
val mcIndex = providers.gradleProperty("mc_index").orNull?.toIntOrNull()
val buildNumber = when {
    !ciRun -> null
    mcIndex != null && runNumber != null -> (runNumber * 100 + mcIndex).toString()
    else -> System.getenv("GITHUB_RUN_NUMBER")
}
version = modVersion + (buildNumber?.let { "+$buildType.$it" } ?: "")
group = property("mod_group_id") as String

base {
    archivesName = "${property("mod_name")}-neoforge-${sc.current.version}"
}

// Minecraft 26.x requires Java 25, older versions run on Java 21
val requiredJava = when {
    sc.current.parsed >= "26" -> 25
    else -> 21
}

java.toolchain.languageVersion = JavaLanguageVersion.of(requiredJava)

repositories {
    mavenLocal()
}

neoForge {
    version = property("neo_version") as String

    if (hasProperty("parchment_mappings_version")) parchment {
        mappingsVersion = property("parchment_mappings_version") as String
        minecraftVersion = property("parchment_minecraft_version") as String
    }

    runs {
        register("client") {
            client()
            gameDirectory = file("../../run/")
            systemProperty("neoforge.enabledGameTestNamespaces", property("mod_id") as String)
        }

        register("server") {
            server()
            gameDirectory = file("../../run/")
            programArgument("--nogui")
            systemProperty("neoforge.enabledGameTestNamespaces", property("mod_id") as String)
        }

        register("gameTestServer") {
            type = "gameTestServer"
            systemProperty("neoforge.enabledGameTestNamespaces", property("mod_id") as String)
        }

        register("data") {
            if (sc.current.parsed >= "26") clientData() else data()

            programArguments.addAll(
                "--mod", property("mod_id") as String,
                "--all",
                "--output", file("src/generated/resources/").absolutePath,
                "--existing", file("src/main/resources/").absolutePath,
            )
        }

        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            logLevel = org.slf4j.event.Level.DEBUG
        }
    }

    mods {
        register(property("mod_id") as String) {
            sourceSet(sourceSets.main.get())
        }
    }
}

sourceSets {
    main {
        resources.srcDir("src/generated/resources")
    }
}

val replaceProperties = mapOf(
    "minecraft_version" to property("minecraft_version"),
    "minecraft_version_range" to property("minecraft_version_range"),
    "neo_version" to property("neo_version"),
    "neo_version_range" to property("neo_version_range"),
    "loader_version_range" to property("loader_version_range"),
    "mod_id" to property("mod_id"),
    "mod_name" to property("mod_name"),
    "mod_license" to property("mod_license"),
    "mod_version" to version,
    "mod_authors" to property("mod_authors"),
    "mod_description" to property("mod_description"),
)

tasks {
    // Link ModDevGradle's Minecraft artifacts to Stonecutter's generated sources
    named("createMinecraftArtifacts") {
        dependsOn("stonecutterGenerate")
    }

    withType<ProcessResources>().configureEach {
        inputs.properties(replaceProperties)

        filesMatching("META-INF/neoforge.mods.toml") {
            expand(replaceProperties)
        }
    }
}

val delombokTask = tasks.named("delombok")

tasks.register<Jar>("sourcesJar") {
    archiveClassifier = "sources"
    dependsOn(delombokTask)
    from(project.files(delombokTask.map { it.outputs.files }))
}

tasks.register<Copy>("buildAndCollect") {
    group = "build"
    description = "Builds the mod jar and copies it to the root build/libs directory"
    dependsOn(tasks.named("build"))
    from(tasks.named("jar").map { (it as Jar).archiveFile }, tasks.named("sourcesJar").map { (it as Jar).archiveFile })
    into(rootProject.layout.buildDirectory.dir("libs/${project.property("mod_version")}"))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = property("mod_group_id") as String
            artifactId = property("mod_name") as String
            version = project.version as String
            from(components["java"])
            artifact(tasks.named("sourcesJar"))
        }
    }
    repositories {
        val mavenUrl = System.getenv("MAVEN_URL")
        if (mavenUrl != null) {
            maven {
                url = uri(mavenUrl)
                credentials {
                    username = System.getenv("MAVEN_USERNAME")
                    password = System.getenv("MAVEN_PASSWORD")
                }
            }
        }
    }
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

lombok {
    // Lombok 1.18.44 is required for Java 25 support and works on Java 21 as well
    version.set("1.18.44")
}

