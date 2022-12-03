package com.github.frcsty.registry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class DayClass {

    public void process() { }

    public void processSecond() { }

    public void displayResult() { }

    public void displaySecondResult() { }

    public BufferedReader readInput(final DayClass clazz) {
        try {
            return new BufferedReader(new FileReader("D:\\Projects\\IntelliJ\\Random\\AdventOfCode2022\\src\\main\\resources\\puzzle_input\\" + clazz.getClass().getSimpleName()));
        } catch (final IOException exception) {
            throw new RuntimeException("Failed to read file for class '" + clazz + "'!");
        }
    }

    public List<Set<String>> readGroupedInput(final DayClass clazz, final int combined) {
        final BufferedReader reader = readInput(clazz);
        final List<String> lines = reader.lines().toList();

        final List<Set<String>> result = new ArrayList<>();
        Set<String> collection = new HashSet<>();
        for (int index = 0; index < lines.size(); index++) {
            collection.add(lines.get(index));

            if ((index + 1) % combined == 0) {
                result.add(collection);
                collection = new HashSet<>();
            }
        }

        return result;
    }

    public Pair<String, String> argumentLine(final String input, final String spliterator) {
        final String[] split = input.split(spliterator);
        return new Pair<>(split[0], split[1]);
    }

    public record Pair<T, Z>(T first, Z second) { }

}
