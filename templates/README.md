# TamboUI Toolkit Templates

This directory contains minimal project templates for getting started with TamboUI Toolkit applications.

## Available Templates

### Maven Template (`maven/`)

A complete Maven project with:
- `pom.xml` configured with TamboUI dependencies
- Standard Maven directory structure
- Executable JAR support via Maven Shade plugin
- Run with `mvn exec:java` or `mvn package && java -jar target/...jar`

**Best for**: Projects that already use Maven or need Maven ecosystem integration.

### Gradle Template (`gradle/`)

A complete Gradle project with:
- `build.gradle.kts` (Kotlin DSL) configured with TamboUI dependencies
- Standard Gradle directory structure
- Application plugin for easy execution
- Run with `./gradlew run` or `./gradlew installDist`

**Best for**: Modern Java projects, Kotlin projects, or projects preferring Gradle's flexibility.

### JBang Template (`jbang/`)

A single-file application that can be run directly:
- No build system required
- Dependencies declared inline via `//DEPS` comments
- Run with `jbang HelloToolkitApp.java` or make executable and run directly

**Best for**: Quick prototypes, scripts, or when you want the simplest possible setup.

## Quick Start

Choose a template based on your preference:

```bash
# Maven
cp -r templates/maven my-app
cd my-app
mvn exec:java

# Gradle
cp -r templates/gradle my-app
cd my-app
./gradlew run

# JBang
cp templates/jbang/HelloToolkitApp.java my-app.java
jbang my-app.java
```

## What's Included

Each template includes:

- **HelloToolkitApp.java**: A minimal working example that demonstrates:
  - Extending `ToolkitApp`
  - Using the Toolkit DSL with static imports
  - Creating a simple UI with `panel()` and `text()`
  - Handling keyboard events
  - Quitting the application

- **Build configuration**: Maven/Gradle files configured with:
  - TamboUI Toolkit dependency
  - TamboUI JLine3 Backend dependency
  - Snapshot repository configuration
  - Java 21 compatibility

- **Documentation**: README files explaining how to use and customize each template

## Customization

All templates follow the same pattern:

1. **Update project metadata**: Change group/artifact/version in build files
2. **Modify the application**: Edit `HelloToolkitApp.java` (or rename it)
3. **Add dependencies**: Add them to your build file or `//DEPS` comments
4. **Customize the UI**: Modify the `render()` method to build your interface

## Requirements

- **Java**: 21 or later (all templates)
- **Build tools**: 
  - Maven 3.6+ (for Maven template)
  - Gradle 8.5+ (for Gradle template)
  - JBang (for JBang template)

## TamboUI Version

All templates use `LATEST` which resolves to the latest snapshot. To pin a specific version:

1. Check available versions: https://central.sonatype.com/repository/maven-snapshots/dev/tamboui/
2. Replace `LATEST` with the specific version in your build file or `//DEPS` comments

## Next Steps

- Explore the [TamboUI documentation](https://tamboui.dev)
- Check out [example demos](https://github.com/tamboui/tamboui/tree/main/demos)
- Read the [API documentation](https://tamboui.dev/docs) for available widgets and components

## Support

- [GitHub Issues](https://github.com/tamboui/tamboui/issues)
- [Zulip Chat](https://tamboui.zulipchat.com)
