package com.lzq.jsyy.msm.utils;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * @author lzq
 */
public class RandomUtil {

    private static final Random RANDOM = new Random();

    private static final DecimalFormat FOURDF = new DecimalFormat("0000");

    private static final DecimalFormat SIXDF = new DecimalFormat("000000");

    public static String getFourBitRandom() {
        return FOURDF.format(RANDOM.nextInt(10000));
    }

    public static String getSixBitRandom() {
        return SIXDF.format(RANDOM.nextInt(1000000));
    }

}
