package com.github.frcsty.registry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public abstract class DayClass {

    private static final Calendar CALENDAR = Calendar.getInstance();
    private static final String PUZZLE_INPUT = "D:\\Projects\\IntelliJ\\Random\\AdventOfCode2022\\src\\main\\resources\\puzzle_input\\day_";

    public void process() { }

    public void processSecond() { }

    public void displayResult() { }

    public void displaySecondResult() { }

    public BufferedReader readInput() {
        try {
            return new BufferedReader(new FileReader(PUZZLE_INPUT + today()));
        } catch (final IOException exception) {
            throw new RuntimeException("Failed to read file for class '" + this + "'!");
        }
    }

    private int today() {
        return CALENDAR.get(Calendar.DAY_OF_MONTH);
    }

    public Pair<LinkedList<String>, LinkedList<String>> readSplitInput() {
        final BufferedReader reader = readInput();
        final LinkedList<String> lines = new LinkedList<>(reader.lines().toList());

        final LinkedList<String> first = new LinkedList<>();
        final LinkedList<String> second = new LinkedList<>();

        boolean initial = true;
        for (final String line : lines) {
            if (line.length() == 0) {
                initial = false;
                continue;
            }

            if (initial) first.add(line);
            else second.add(line);
        }

        final LinkedList<String> firstReversed = new LinkedList<>();
        while (!first.isEmpty()) {
            firstReversed.add(first.removeLast());
        }

        return new Pair<>(firstReversed, second);
    }

    public List<Set<String>> readGroupedInput(final int combined) {
        final BufferedReader reader = readInput();
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

    public TriPair<String, String, String> splitCommand(final String input, final String spliterator) {
        final String[] content = input.split(spliterator);
        final String command = content[0];

        if (command.equalsIgnoreCase("$")) return new TriPair<>(command, content[1], content.length == 3 ? content[2] : null);
        return new TriPair<>(null, content[0], content[1]);
    }

    public record Pair<T, Z>(T first, Z second) { }

    public record TriPair<T, Z, Y>(T first, Z second, Y third) { }

}
