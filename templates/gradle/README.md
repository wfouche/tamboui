# Gradle Template for TamboUI Toolkit

This is a minimal Gradle project template for creating TamboUI Toolkit applications.

## Project Structure

```
.
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── README.md
└── src/
    └── main/
        └── java/
            └── dev/
                └── tamboui/
                    └── HelloToolkitApp.java
```

## Requirements

- Java 21 or later
- Gradle 8.5 or later (wrapper included)

## Getting Started

1. **Copy this template** to your project directory:
   ```bash
   cp -r templates/gradle my-tamboui-app
   cd my-tamboui-app
   ```

2. **Update the project name** in `settings.gradle.kts`:
   ```kotlin
   rootProject.name = "my-tamboui-app"
   ```

3. **Update project coordinates** in `build.gradle.kts`:
   - Change `group` and `version` to match your project
   - Update the `mainClass` in the `application` block if you rename the class

4. **Build the project**:
   ```bash
   ./gradlew build
   ```

5. **Run the application**:
   ```bash
   ./gradlew run
   ```

   Or build the distribution and run it:
   ```bash
   ./gradlew installDist
   ./build/install/hello-toolkit-app/bin/hello-toolkit-app
   ```

## Customization

- **Change Java version**: Update `JavaLanguageVersion.of(21)` in `build.gradle.kts`
- **Add dependencies**: Add them to the `dependencies` block in `build.gradle.kts`
- **Modify the application**: Edit `src/main/java/dev/tamboui/HelloToolkitApp.java`

## TamboUI Version

The template uses `0.1.0-SNAPSHOT` by default. To use a different version:

1. Check available versions at https://central.sonatype.com/repository/maven-snapshots/dev/tamboui/
2. Update the version in the `dependencies` block in `build.gradle.kts`

## Gradle Wrapper

If you don't have Gradle installed, you can use the wrapper:
```bash
./gradlew --version
```

To generate the wrapper files:
```bash
gradle wrapper --gradle-version 8.5
```

## Learn More

- [TamboUI Documentation](https://tamboui.dev)
- [TamboUI Examples](https://github.com/tamboui/tamboui/tree/main/demos)
