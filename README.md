# java-docker-cli

A minimal Docker example demonstrating how to containerize a simple Java CLI application using a multi-stage build.

## What's inside

This project contains:
- **HelloWorld.java** — A simple Java CLI program that accepts command-line arguments
- **Dockerfile** — Multi-stage Docker build that compiles Java and creates a lean runtime image
- **.dockerignore** — Build context exclusions (Git files, documentation)

## Building the image

```bash
docker build -t java-hello-world .
```

## Running the container

Run with default output:
```bash
docker run java-hello-world
```

Output:
```
Hello, World!
```

Run with a custom argument:
```bash
docker run java-hello-world Alice
```

Output:
```
Hello, Alice!
```

## How it works

The Dockerfile uses a two-stage build process:

1. **Build stage** — Uses `eclipse-temurin:21-jdk-alpine` to compile `HelloWorld.java` into bytecode
2. **Runtime stage** — Uses the smaller `eclipse-temurin:21-jre-alpine` image and copies only the compiled `.class` file

This approach keeps the final image size small by excluding the JDK and source files from the runtime container.

## License

MIT
