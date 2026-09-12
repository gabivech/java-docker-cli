package org.textcli;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.List;

// Persists a simple audit log of commands run, so `history` works across container runs
// when TEXTCLI_HISTORY_FILE points at a mounted volume.
public final class HistoryStore {
    private HistoryStore() {
    }

    public static Path historyFile() {
        String override = System.getenv("TEXTCLI_HISTORY_FILE");
        if (override != null && !override.isBlank()) {
            return Path.of(override);
        }
        return Path.of(System.getProperty("user.home"), ".textcli", "history.log");
    }

    public static void append(String command, String[] args) {
        Path file = historyFile();
        try {
            if (file.getParent() != null) {
                Files.createDirectories(file.getParent());
            }
            String line = Instant.now() + "\t" + command + " " + String.join(" ", args) + System.lineSeparator();
            Files.writeString(file, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Warning: could not write history: " + e.getMessage());
        }
    }

    public static List<String> recent(int limit) {
        Path file = historyFile();
        if (!Files.exists(file)) {
            return List.of();
        }
        try {
            List<String> lines = Files.readAllLines(file);
            int from = Math.max(0, lines.size() - limit);
            return lines.subList(from, lines.size());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
