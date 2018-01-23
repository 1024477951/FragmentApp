package com.fragmentapp.helper;

import java.util.Random;

/**
 * Created by liuzhen on 2018/1/18.
 */

public class RandomUtil {

    private static Random rand = new Random();

    public static float random(int max){
        float result = (rand.nextInt(max) + 1) * 0.1f;
        return result;
    }

    public static int random(int min,int max){
        int result = rand.nextInt(max - min) + min;
        return result;
    }

}
