package ru.job4j.io;

import java.io.*;
import java.nio.charset.StandardCharsets;

public final class WriteFile {
    private final File file;

    public WriteFile(File file) {
        this.file = file;
    }

    /**
     * пишет строку в файл
     */
    public synchronized void saveContent(final String content) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            byte[] buffer = content.getBytes(StandardCharsets.UTF_8);
            out.write(buffer);
        }
    }
}

