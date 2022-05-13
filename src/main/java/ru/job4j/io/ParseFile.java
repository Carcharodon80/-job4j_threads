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
    private synchronized String getContent(final Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = in.read()) != -1) {
                char c = (char) data;
                if (filter.test(c)) {
                    output.append(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public synchronized String getContentWithoutUnicode() {
        return getContent(x -> x < 0x80);
    }

    public synchronized String getAllContent() {
        return getContent(x -> true);
    }

    public static void main(String[] args) {
        ParseFile parseFile = new ParseFile(new File("in.txt"));
        WriteFile writeFile = new WriteFile(new File("out.txt"));
        writeFile.saveContent(parseFile.getContentWithoutUnicode());
    }
}
