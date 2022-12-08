package com.github.frcsty.day;

import com.github.frcsty.registry.DayClass;
import com.github.frcsty.registry.util.ConsoleColors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public final class Day_8 extends DayClass {

    private static final AtomicLong COUNT = new AtomicLong(0);
    private static final AtomicLong SECOND_COUNT = new AtomicLong(0);

    private static final Map<Integer, List<Tree>> TREES = new HashMap<>();

    @Override
    public void process() {
        final AtomicInteger atomicRow = new AtomicInteger(0);

        readInput().lines().forEach(line -> {
            final int currentRow = atomicRow.getAndIncrement();
            final char[] heights = line.toCharArray();
            final List<Tree> content = new ArrayList<>();

            for (int column = 0; column < line.length(); column++) {
                final int value = Integer.parseInt(String.valueOf(heights[column]));

                content.add(new Tree(column, currentRow, value));
            }

            TREES.put(currentRow, content);
        });

        for (int z = 0; z < TREES.size(); z++) {
            final List<Tree> row = TREES.get(z);
            for (final Tree tree : row) {
                if (visible(tree).first()) {
                    System.out.print(ConsoleColors.GREEN + tree.height + ConsoleColors.RESET);
                    COUNT.incrementAndGet();
                }
                else System.out.print(ConsoleColors.RED + tree.height + ConsoleColors.RESET);
            }

            System.out.print("\n");
        }
    }

    @Override
    public void processSecond() {
        for (int z = 0; z < TREES.size(); z++) {
            final List<Tree> row = TREES.get(z);
            for (final Tree tree : row) {
                final int scenicScore = visible(tree).second();
                final long current = SECOND_COUNT.get();

                if (scenicScore > current) SECOND_COUNT.set(scenicScore);
                System.out.print(ConsoleColors.GREEN + scenicScore + ConsoleColors.RESET);
            }

            System.out.print("\n");
        }
    }

    @Override
    public void displayResult() {
        System.out.println(COUNT.get());
    }

    @Override
    public void displaySecondResult() {
        System.out.println(SECOND_COUNT.get());
    }

    private Pair<Boolean, Integer> visible(final Tree tree) {
        int scenicScore = 1;
        int visibilityIndex = 4;

        // left
        int distance = 0;
        for (int x = tree.x - 1; x >= 0; x--) {
            final Tree neighbour = forLocation(x, tree.z);
            distance ++;
            if (neighbour.isTaller(tree)) {
                visibilityIndex -= 1;
                break;
            }
        }

        scenicScore *= distance;
        distance = 0;

        // right
        for (int x = tree.x + 1; x < TREES.get(tree.z).size(); x++) {
            final Tree neighbour = forLocation(x, tree.z);
            distance ++;
            if (neighbour.isTaller(tree)) {
                visibilityIndex -= 1;
                break;
            }
        }

        scenicScore *= distance;
        distance = 0;

        // top
        for (int z = tree.z - 1; z >= 0; z--) {
            final Tree neighbour = forLocation(tree.x, z);
            distance++;
            if (neighbour.isTaller(tree)) {
                visibilityIndex -= 1;
                break;
            }
        }

        scenicScore *= distance;
        distance = 0;

        // bottom
        for (int z = tree.z + 1; z < TREES.size(); z++) {
            final Tree neighbour = forLocation(tree.x, z);
            distance++;
            if (neighbour.isTaller(tree)) {
                visibilityIndex -= 1;
                break;
            }
        }

        scenicScore *= distance;
        return new Pair<>(visibilityIndex >= 1, scenicScore);
    }

    private Tree forLocation(final int x, final int z) {
        return TREES.get(z).get(x);
    }

    private record Tree(int x, int z, int height) {
        boolean isTaller(final Tree tree) {
            return this.height >= tree.height;
        }
    }

}
