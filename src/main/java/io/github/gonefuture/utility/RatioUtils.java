package io.github.gonefuture.utility;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 权重工具
 *
 * @author gonefuture
 * @version 2021/04/21 17:31
 */
public class RatioUtils {

    /**
     *  权重随机接口
     */
    public interface IRatio {
        /**
         *  权重随机接口
         */
        int weight();
    }
    public static <T extends IRatio> List<T> baseRandomList(List<T> all, int count, boolean noRepeat, Random random) {
        if (noRepeat) {
            all = new LinkedList<>(all);
        }
        List<T> hitList = new ArrayList<>(count);
        int totalWeight = getTotalWeight(all);
        for (int i = 0; i < count; i++) {
            T t = baseRandomOne(all, totalWeight, random);
            if (t == null) {
                continue;
            }
            hitList.add(t);
            if (noRepeat) {
                all.remove(t);
                totalWeight -= t.weight();
            }
        }
        return hitList;

    }

    private static <T extends IRatio> int getTotalWeight(List<T> ratios) {
        int totalWeight = 0;
        for (T t : ratios) {
            int weight = t.weight();
            if (weight <= 0) {
                throw new IllegalArgumentException(String.format("weight %d is <= 0", weight));
            }
            totalWeight += weight;
        }
        return totalWeight;
    }


    public static <T extends IRatio> T baseRandomOne(List<T> ratios, int totalWeight, Random random) {
        if (ratios.isEmpty()) {
            return null;
        }
        if (totalWeight <= 0) {
            throw new IllegalArgumentException("totalWeight is " + 0);
        }
        int hit = random.nextInt(totalWeight);
        for (T t : ratios) {
            if (hit < t.weight()) {
                return t;
            }
            hit -= t.weight();
        }
        return null;
    }
}
