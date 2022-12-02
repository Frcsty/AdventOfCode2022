package com.github.frcsty.registry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class DayClass {

    public void process() { }

    public void processSecond() { }

    public void displayResult() { }

    public void displaySecondResult() { }

    public BufferedReader readInput(final DayClass clazz) {
        try {
            return new BufferedReader(new FileReader("D:\\Projects\\IntelliJ\\Random\\AdventOfCode2022\\src\\main\\resources\\puzzle_input\\" + clazz.getClass().getSimpleName()));
        } catch (final IOException exception) {
            return null;
        }
    }

    public Pair<String, String> argumentLine(final String input, final String spliterator) {
        final String[] split = input.split(spliterator);
        return new Pair<>(split[0], split[1]);
    }

    public record Pair<T, Z>(T first, Z second) { }

}
