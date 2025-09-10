package io.github.terraria.utils;

public class MathUtils {
    public static int floor(float value) { return (int) Math.floor(value); }

    public static int ceil(float value) { return (int) Math.ceil(value); }

    public static int binomialRandom(int m) {
        final int n = 2 * m;
        int a = 0;
        for(int i = 1; i <= n; i++)
            if(Math.random() < 0.5) a++;
        return a - m;
    }
}
