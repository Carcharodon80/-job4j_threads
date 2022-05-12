package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    /**
     * читает файл, тестирует каждый символ на Predicate<Character> filter и добавляет в строку
     */
    public synchronized String getContentWithoutUnicode(final Predicate<Character> filter) throws IOException {
        String output = "";
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = in.read()) > 0) {
                char c = (char) data;
                if (filter.test(c)) {
                    output += c;
                }
            }
        }
        return output;
    }

    public static void main(String[] args) throws IOException {
        ParseFile parseFile = new ParseFile(new File("in.txt"));
        WriteFile writeFile = new WriteFile(new File("out.txt"));
        writeFile.saveContent(parseFile.getContentWithoutUnicode(x -> x < 0x80));
    }
}
