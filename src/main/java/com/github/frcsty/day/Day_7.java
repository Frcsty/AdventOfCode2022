package com.github.frcsty.day;

import com.github.frcsty.registry.DayClass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class Day_7 extends DayClass {

    private static final Directory BASE_DIRECTORY = Directory.constructDirectory(null, "/");

    @Override
    public void process() {
        final AtomicReference<Directory> current = new AtomicReference<>(BASE_DIRECTORY);

        readInput().lines().forEach(line -> {
            final TriPair<String, String, String> content = splitCommand(line, " ");

            if (content.first() != null) {
                final String command = content.second();
                final String argument = content.third();

                switch (command.toLowerCase()) {
                    case "cd" -> {
                        switch (argument.toLowerCase()) {
                            case ".." -> {
                                final Directory directory = current.get();

                                current.set(directory.parent == null ? BASE_DIRECTORY : directory.parent);
                            }
                            case "/" -> current.set(BASE_DIRECTORY);
                            default -> {
                                final Directory directory = byName(current.get(), argument);
                                if (directory == null) return;

                                current.set(directory);
                            }
                        }
                    }
                    case "ls" -> {
                    } //print();
                }
                return;
            }

            final Directory parent = current.get();
            final Directory constructed = Directory.construct(parent, content.second(), content.third());

            parent.addChild(constructed);
        });

        print();
    }

    @Override
    public void displayResult() {
        BASE_DIRECTORY.directories.forEach(it -> System.out.println(it.count()));
    }

    private void print() {
        final StringBuilder builder = new StringBuilder();
        extend(BASE_DIRECTORY, builder, 0);
        System.out.println(builder);
    }

    private void extend(final Directory directory, final StringBuilder builder, int indentation) {
        builder.append(" ".repeat(indentation));
        builder.append(" - ").append(directory.name).append(" ");

        if (directory.size == -1) builder.append("(dir)");
        else builder.append("(file, size=").append(directory.size).append(")");
        builder.append("\n");

        if (directory.directories.size() != 0)
            directory.directories.forEach(it -> extend(it, builder, indentation + 2));
    }

    private Directory byName(final Directory current, final String name) {
        return current.directories.stream().filter(it -> it.name.equalsIgnoreCase(name)).findAny().orElse(null);
    }

    private static class Directory {

        private final List<Directory> directories = new ArrayList<>();
        private final int size;
        private final String name;
        private final Directory parent;

        Directory(final Directory parent, final String name, final int size) {
            this.parent = parent;
            this.name = name;
            this.size = size;
        }

        static Directory constructFile(final Directory parent, final String name, final int size) {
            return new Directory(parent, name, size);
        }

        static Directory constructDirectory(final Directory parent, final String name) {
            return new Directory(parent, name, -1);
        }

        static Directory construct(final Directory parent, final String first, final String second) {
            if (first.equalsIgnoreCase("dir")) return constructDirectory(parent, second);

            return constructFile(parent, second, Integer.parseInt(first));
        }

        void addChild(final Directory directory) {
            this.directories.add(directory);
        }

        long count() {
            long count = this.size == -1 ? 0 : this.size;

            for (int index = 0; index < this.directories.size(); index++) {
                count += this.directories.get(index).count();
            }

            return count;
        }

    }

}
