package org.abelsromero.demo.test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public final class FilesHandler {

    final Path tempDir;

    public FilesHandler(Path tempDir) {
        this.tempDir = tempDir;
    }

    public Path createFile(String content) throws IOException {
        final Path configFile = java.nio.file.Files.createFile(tempDir.resolve(String.format("full-config-%s.yaml", UUID.randomUUID())));
        java.nio.file.Files.writeString(configFile, content);
        return configFile;
    }
}
