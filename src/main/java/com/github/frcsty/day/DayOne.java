package com.github.frcsty.day;

import com.github.frcsty.registry.DayClass;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class DayOne implements DayClass {

    private final Map<UUID, Integer> count = new HashMap<>();

    @Override
    public void process() {
        try (final BufferedReader reader = new BufferedReader(new FileReader("D:\\Projects\\IntelliJ\\Random\\AdventOfCode2022\\src\\main\\resources\\puzzle_input\\day_1"))) {
            final AtomicInteger calories = new AtomicInteger(0);

            reader.lines().forEach(line -> {
                if (line.length() == 0) {
                    count.put(UUID.randomUUID(), calories.intValue());
                    calories.set(0);
                    return;
                }

                calories.addAndGet(Integer.parseInt(line));
            });

            count.put(UUID.randomUUID(), calories.intValue());
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void displayResult() {
        final List<Integer> sorted = count.values().stream().sorted(Comparator.reverseOrder()).toList();

        int count = 0;
        for (int position = 0; position < 3; position++) {
            count += sorted.get(position);
        }

        System.out.println(count);
    }

}
