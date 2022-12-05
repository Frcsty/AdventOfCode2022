package com.github.frcsty.day;

import com.github.frcsty.registry.DayClass;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public final class Day_5 extends DayClass {

    private static final Map<Integer, LinkedList<Crate>> CARGO = new HashMap<>();
    private static final Map<Integer, LinkedList<Crate>> SECOND_CARGO = new HashMap<>();

    @Override
    public void process() {
        final Pair<LinkedList<String>, LinkedList<String>> input = readSplitInput(this);

        input.first().forEach(current -> {
            String parsed = replaceWhitespaces(current, 4);
            parsed = parsed.replace("[", " ").replace("]", " ");
            parsed = replaceWhitespaces(parsed, 3);
            parsed = parsed.substring(1);
            parsed = replaceWhitespaces(parsed, 2);

            for (int index = 0; index < parsed.length(); index++) {
                final char character = parsed.charAt(index);
                final int currentId = index + 1;
                final String symbol = String.valueOf(character);
                if (symbol.isBlank()) continue;

                try {
                    Integer.parseInt(symbol);
                } catch (final NumberFormatException exception) {
                    final LinkedList<Crate> crates = CARGO.getOrDefault(currentId, new LinkedList<>());
                    crates.add(new Crate(symbol));
                    CARGO.put(currentId, crates);
                    SECOND_CARGO.put(currentId, new LinkedList<>(crates));
                }
            }
        });

        input.second().forEach(line -> {
            final Move move = new Move(line);

            handleSingleMove(move.count, move.start, move.end);
        });
    }

    @Override
    public void processSecond() {
        final Pair<LinkedList<String>, LinkedList<String>> input = readSplitInput(this);

        input.second().forEach(line -> {
            final Move move = new Move(line);
            handleStackedMove(move.count, move.start, move.end);
        });
    }

    @Override
    public void displayResult() {
        System.out.println(CARGO.values().stream().map(LinkedList::getLast).map(it -> it.symbol).collect(Collectors.joining()));
    }

    @Override
    public void displaySecondResult() {
        System.out.println(SECOND_CARGO.values().stream().map(LinkedList::getLast).map(it -> it.symbol).collect(Collectors.joining()));
    }

    private void handleSingleMove(final int count, final int start, final int finish) {
        final LinkedList<Crate> starting = CARGO.get(start);
        final LinkedList<Crate> ending = CARGO.get(finish);

        for (int index = 0; index < count; index++) {
            final Crate crate = starting.pollLast();
            ending.addLast(crate);
        }

        CARGO.put(start, starting);
        CARGO.put(finish, ending);
    }

    private void handleStackedMove(final int count, final int start, final int finish) {
        final LinkedList<Crate> starting = SECOND_CARGO.get(start);
        final LinkedList<Crate> ending = SECOND_CARGO.get(finish);

        for (int index = 0; index < count; index++) {
            final int modified = (starting.size() - count) + index;
            final Crate crate = starting.remove(modified);
            ending.addLast(crate);
        }

        SECOND_CARGO.put(start, starting);
        SECOND_CARGO.put(finish, ending);
    }

    private String replaceWhitespaces(String input, int nth) {
        final StringBuilder builder = new StringBuilder();

        int previous = 0;
        for (int index = nth - 1; index < input.length(); index += nth) {
            builder.append(input, previous, index);
            previous = index + 1;
        }

        return builder.append(input, previous, input.length()).toString();
    }

    private record Crate(String symbol) {

        @Override
        public String toString() {
            return "[" + this.symbol + "]";
        }

    }

    private static class Move {

        private final int count;
        private final int start;
        private final int end;

        Move(final String input) {
            final String[] contents = input.split(" ");
            this.count = Integer.parseInt(contents[1]);
            this.start = Integer.parseInt(contents[3]);
            this.end = Integer.parseInt(contents[5]);
        }

    }

}
