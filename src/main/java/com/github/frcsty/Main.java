package com.github.frcsty;

import com.github.frcsty.day.DayOne;
import com.github.frcsty.registry.DayClass;

import java.util.Optional;
import java.util.Set;

public class Main {

    private static final Set<DayClass> classes = Set.of(
            new DayOne()
    );

    public static void main(String[] args) {
        final String desired = "DayOne";

        final Optional<DayClass> optionalClazz = classes.stream().filter(it ->
                it.getClass().getName().contains(desired)
        ).findFirst();
        if (optionalClazz.isEmpty()) {
            // log
            return;
        }

        final DayClass clazz = optionalClazz.get();

        clazz.process();
        clazz.displayResult();
    }

}