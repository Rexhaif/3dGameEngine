package com.notjuststudio.engine3dgame.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 28.01.2017.
 */
public class ListUtil {

    public static void deleteEqualString(List<String> first, List<String> second) {
        for (int i = 0; i < first.size(); i++) {
            for (int j = 0; j < second.size(); j++) {
                if (first.get(i).equals(second.get(j))) {
                    first.remove(i);
                    second.remove(j);
                    i--; j--;
                }
            }
        }
    }

    public static void addUnrepeatedString(List<String> target, List<String> source) {
        for (String str : source) {
            if (!target.contains(str)) {
                target.add(str);
            }
        }
    }

    public static List<String> createMinusString(List<String> minuend, List<String> subtrahend) {
        List<String> result = new ArrayList<>();
        for (String str : minuend) {
            if (!subtrahend.contains(str)) {
                result.add(str);
            }
        }
        return result;
    }

    public static List<String> createSameString(List<String> first, List<String> second) {
        List<String> result = new ArrayList<>();

        for (String str1 : first) {
            for (String str2 : second) {
                if (str1.equals(str2)) {
                    result.add(str1);
                }
            }
        }

        return result;
    }
}
