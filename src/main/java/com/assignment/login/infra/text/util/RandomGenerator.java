package com.assignment.login.infra.text.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {
    private static final Integer MIN_NUMBER = 100000;
    private static final Integer MAX_NUMBER = 1000000;
    private RandomGenerator() {

    }

    public static String rand() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(MIN_NUMBER, MAX_NUMBER));
    }
}
