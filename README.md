# java-docker-cli

A small Java CLI for quick text and file operations, packaged in a multi-stage Docker image.
This project is meant to be simple, useful, and easy to extend as a base for more advanced CLI tools.

## Features

- `count <file>`: counts lines, words and characters
- `search <pattern> <file>`: prints matching lines using a regular expression
- `replace <pattern> <replacement> <file>`: replaces matches and prints the result
- `history [limit]`: shows recent commands executed by the CLI

## Project structure

- `src/main/java/org/textcli`: Java source code for the CLI
- `pom.xml`: Maven configuration
- `Dockerfile`: multi-stage Docker build
- `samples/`: example files for local testing
- `data/`: persistent history folder when mounted as a Docker volume

## Build locally

```bash
mvn package
java -jar target/textcli.jar --help
```

## Run locally

```bash
java -jar target/textcli.jar count samples/example.txt
java -jar target/textcli.jar search "foo" samples/example.txt
java -jar target/textcli.jar replace "foo" "BAR" samples/example.txt
java -jar target/textcli.jar history
```

## Build the Docker image

```bash
docker build -t textcli .
```

## Run in Docker

```bash
docker run --rm -v "$(pwd)/data:/data" -v "$(pwd)/samples:/samples" textcli count /samples/example.txt
```

```bash
docker run --rm -v "$(pwd)/data:/data" -v "$(pwd)/samples:/samples" textcli search "foo" /samples/example.txt
```

```bash
docker run --rm -v "$(pwd)/data:/data" -v "$(pwd)/samples:/samples" textcli replace "foo" "BAR" /samples/example.txt
```

```bash
docker run --rm -v "$(pwd)/data:/data" textcli history
```

The `--rm` flag removes the container after the command finishes, which fits the one-shot nature of this CLI.

## How it works

The Dockerfile uses two stages:

1. Build stage: Maven compiles the project and packages it into a JAR file.
2. Runtime stage: a lean Java runtime image executes the JAR as a non-root user.

This keeps the final image small while still being usable for real CLI tasks.

## License

MIT

