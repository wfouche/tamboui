# Maven Template for TamboUI Toolkit

This is a minimal Maven project template for creating TamboUI Toolkit applications.

## Project Structure

```
.
├── pom.xml
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
- Maven 3.6 or later

## Getting Started

1. **Copy this template** to your project directory:
   ```bash
   cp -r templates/maven my-tamboui-app
   cd my-tamboui-app
   ```

2. **Update the project coordinates** in `pom.xml`:
   - Change `groupId`, `artifactId`, and `version` to match your project
   - Update the `mainClass` in the exec-maven-plugin configuration if you rename the class

3. **Build the project**:
   ```bash
   mvn clean compile
   ```

4. **Run the application**:
   ```bash
   mvn exec:java
   ```

   Or run the executable JAR:
   ```bash
   mvn package
   java -jar target/hello-toolkit-app-1.0.0-SNAPSHOT.jar
   ```

## Customization

- **Change Java version**: Update `maven.compiler.source` and `maven.compiler.target` in `pom.xml`
- **Add dependencies**: Add them to the `<dependencies>` section in `pom.xml`
- **Modify the application**: Edit `src/main/java/dev/tamboui/HelloToolkitApp.java`

## TamboUI Version

The template uses `LATEST` version which will resolve to the latest snapshot. To use a specific version:

1. Check available versions at https://central.sonatype.com/repository/maven-snapshots/dev/tamboui/
2. Update the `tamboui.version` property in `pom.xml`

## Learn More

- [TamboUI Documentation](https://tamboui.dev)
- [TamboUI Examples](https://github.com/tamboui/tamboui/tree/main/demos)
