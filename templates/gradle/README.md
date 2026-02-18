# Gradle Template for TamboUI Toolkit

This is a minimal Gradle project template for creating TamboUI Toolkit applications.

## Requirements

- Java 21 or later
- Gradle 8+ or 9+ (to be compatible with Java 25)

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

   Here `gradle run` will not work, because it runs Java via a daemon that has no terminal.

   Instead, you can run the application with `java -jar`:
   ```bash
   java -jar build/libs/hello-toolkit-app-0.1.0-SNAPSHOT-all.jar
   ```

   Or build the distribution and run it:
   ```bash
   ./gradlew installDist
   ./build/install/hello-toolkit-app/bin/hello-toolkit-app
   ```

   or run it with `jbang run`:
   ```bash
   jbang run build/libs/hello-toolkit-app-0.1.0-SNAPSHOT-all.jar
   ```
   
   The latter has the advantage you can run with `jbang run --debug` to attach a debugger.


## Learn More

- [TamboUI Documentation](https://tamboui.dev)
