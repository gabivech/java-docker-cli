# Build stage
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /build
COPY HelloWorld.java .
RUN javac HelloWorld.java

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /build/HelloWorld.class .
ENTRYPOINT ["java", "HelloWorld"]
