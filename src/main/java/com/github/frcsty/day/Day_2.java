package com.github.frcsty.day;

import com.github.frcsty.registry.DayClass;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public final class Day_2 extends DayClass {

    private static final AtomicLong COUNT = new AtomicLong(0);
    private static final AtomicLong SECOND_COUNT = new AtomicLong(0);

    @Override
    public void process() {
        readInput(this).lines().forEach(line -> {
            final Pair<String, String> input = argumentLine(line, " ");

            final GameMapping opponent = GameMapping.mappingFor(input.first());
            final GameMapping self = GameMapping.mappingFor(input.second());

            final ResultMapping result = GameMapping.resultFor(opponent, self);
            COUNT.getAndAdd(result.score + self.score);
        });
    }

    @Override
    public void processSecond() {
        readInput(this).lines().forEach(line -> {
            final Pair<String, String> input = argumentLine(line, " ");

            final GameMapping opponent = GameMapping.mappingFor(input.first());
            final ResultMapping result = ResultMapping.resultFor(input.second());

            final GameMapping response = GameMapping.responseFor(opponent, result);
            SECOND_COUNT.getAndAdd(result.score + response.score);
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

    private enum GameMapping {

        ROCK("A", "X", "B", 1),
        PAPER("B", "Y", "C", 2),
        SCISSORS("C", "Z", "A", 3);

        private final String symbol;
        private final String secondSymbol;

        private final String counter;
        private final int score;

        GameMapping(final String symbol, final String secondSymbol, final String counter, final int score) {
            this.symbol = symbol;
            this.secondSymbol = secondSymbol;

            this.counter = counter;
            this.score = score;
        }

        static GameMapping mappingFor(final String symbol) {
            return Arrays.stream(values()).filter(it -> it.symbol.equalsIgnoreCase(symbol) || it.secondSymbol.equalsIgnoreCase(symbol)).findFirst().get();
        }

        static GameMapping counterFor(final GameMapping mapping) {
            return Arrays.stream(values()).filter(it -> it.counter.equalsIgnoreCase(mapping.symbol)).findFirst().get();
        }

        static ResultMapping resultFor(final GameMapping opponent, final GameMapping self) {
            if (opponent.symbol.equals(self.symbol)) {
                return ResultMapping.DRAW;
            }

            if (opponent.counter.equals(self.symbol)) {
                return ResultMapping.WON;
            }

            return ResultMapping.LOSS;
        }

        static GameMapping responseFor(final GameMapping opponent, final ResultMapping result) {
            GameMapping response = null;

            switch (result) {
                case DRAW -> response = opponent;
                case WON -> response = GameMapping.mappingFor(opponent.counter);
                case LOSS -> response = GameMapping.counterFor(opponent);
            }

            return response;
        }

    }

    private enum ResultMapping {

        LOSS(0, "X"),
        DRAW(3, "Y"),
        WON(6, "Z");

        private final int score;
        private final String symbol;

        ResultMapping(final int score, final String symbol) {
            this.score = score;
            this.symbol = symbol;
        }

        static ResultMapping resultFor(final String symbol) {
            return Arrays.stream(values()).filter(it -> it.symbol.equalsIgnoreCase(symbol)).findFirst().get();
        }

    }

}
