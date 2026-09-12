# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn -B -q package

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Create non-root user for security
RUN addgroup -g 1001 appgroup && \
    adduser -D -u 1001 -G appgroup appuser

# Default history location; mount a volume here to persist it across container runs
ENV TEXTCLI_HISTORY_FILE=/data/history.log
RUN mkdir -p /data && chown appuser:appgroup /data
VOLUME ["/data"]

COPY --from=builder /build/target/textcli.jar app.jar
USER appuser

ENTRYPOINT ["java", "-jar", "app.jar"]
