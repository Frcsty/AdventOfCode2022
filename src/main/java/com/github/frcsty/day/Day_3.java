package com.github.frcsty.day;

import com.github.frcsty.registry.DayClass;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public final class Day_3 extends DayClass {

    private static final String LETTER_PRIORITIES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final AtomicLong COUNT = new AtomicLong(0);
    private static final AtomicLong SECOND_COUNT = new AtomicLong(0);

    @Override
    public void process() {
        readInput(this).lines().forEach(line -> {
            final Character character = Compartment.shared(compartments(line));

            COUNT.getAndAdd(priority(character));
        });
    }

    @Override
    public void processSecond() {
        readGroupedInput(this, 3).forEach(group -> {
            final Character character = Compartment.shared(group.stream().map(Compartment::new).toList());

            SECOND_COUNT.getAndAdd(priority(character));
        });
    }

    @Override
    public void displayResult() {
        System.out.println(COUNT.get());
    }

    @Override
    public void displaySecondResult() {
        System.out.println(SECOND_COUNT.get());
    }

    private int priority(final char character) {
        return LETTER_PRIORITIES.indexOf(character) + 1;
    }

    private List<Compartment> compartments(final String input) {
        return List.of(
                new Compartment(input.substring(0, input.length() / 2)),
                new Compartment(input.substring(input.length() / 2))
        );
    }

    private record Compartment(String contents) {

        private boolean contains(final char character) {
            return this.contents.contains(String.valueOf(character));
        }

        private static Character shared(final List<Compartment> compartments) {
            Character result = null;

            for (final char character : LETTER_PRIORITIES.toCharArray()) {
                if (compartments.stream().filter(it -> it.contains(character)).count() < compartments.size()) {
                    continue;
                }

                result = character;
                break;
            }

            return result;
        }

    }

}
