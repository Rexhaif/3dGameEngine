package com.notjuststudio.engine3dgame.util;

/**
 * Created by George on 21.01.2017.
 */
public class FlagsUtil {

    public static boolean flagChecker(int x, int flag) {
        return (x & flag) == flag;
    }

}
