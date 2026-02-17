# JBang Template for TamboUI Toolkit

This is a minimal JBang template for creating TamboUI Toolkit applications that can be run directly without a build system.

## Project Structure

```
.
├── HelloToolkitApp.java
└── README.md
```

## Requirements

- Java 21 or later
- JBang installed (see https://www.jbang.dev/download/)

## Getting Started

1. **Copy this template** to your project directory:
   ```bash
   cp templates/jbang/HelloToolkitApp.java my-app.java
   ```

2. **Run the application directly**:
   ```bash
   jbang my-app.java
   ```

   Or make it executable and run:
   ```bash
   chmod +x my-app.java
   ./my-app.java
   ```

## How It Works

JBang allows you to run Java files directly without compilation or a build system. The file includes:

- **Shebang line**: `///usr/bin/env jbang "$0" "$@" ; exit $?` - Makes the file executable
- **Dependencies**: `//DEPS` directives specify Maven coordinates
- **Repositories**: `//REPOS` directives specify Maven repositories
- **Source code**: Standard Java code that extends `ToolkitApp`

## Customization

- **Rename the file**: Change `HelloToolkitApp.java` to your desired name
- **Update the class name**: Change `HelloToolkitApp` to match your filename
- **Add dependencies**: Add more `//DEPS` lines:
  ```java
  //DEPS com.example:library:1.0.0
  ```
- **Modify the application**: Edit the `render()` method and add your UI code

## TamboUI Version

The template uses `LATEST` version which will resolve to the latest snapshot. To use a specific version:

1. Check available versions at https://central.sonatype.com/repository/maven-snapshots/dev/tamboui/
2. Replace `LATEST` with the specific version in the `//DEPS` lines

## JBang Shortcuts

If you're using JBang 0.136 or higher, you can use shortcuts:

```java
//REPOS central-portal-snapshots
```

Instead of the full repository URL.

## IDE Support

JBang files work well in IDEs:
- **IntelliJ IDEA**: Install the JBang plugin for syntax highlighting and dependency resolution
- **VS Code**: Install the JBang extension
- **Eclipse**: Basic Java editing works, but dependency resolution may require manual setup

## Learn More

- [JBang Documentation](https://www.jbang.dev/documentation/)
- [TamboUI Documentation](https://tamboui.dev)
- [TamboUI Examples](https://github.com/tamboui/tamboui/tree/main/demos)
