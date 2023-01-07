package com.github.victortedesco.betterdeathscreen.api.utils;

import java.util.List;
import java.util.Random;

public class Randomizer {

    public <T> T getRandomItemFromList(List<T> list) {
        Random random = new Random();
        int item = random.nextInt(list.size());

        return list.get(item);
    }

    public <T> T getRandomItemFromArray(T[] array) {
        Random random = new Random();
        int item = random.nextInt(array.length);

        return array[item];
    }
}
