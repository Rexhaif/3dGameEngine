package com.notjuststudio.engine3dgame.display;

import org.lwjgl.opengl.DisplayMode;

import java.util.Comparator;

/**
 * Created by Georgy on 26.02.2017.
 */
public class DisplayModeComparator implements Comparator<DisplayMode> {
    @Override
    public int compare(DisplayMode first, DisplayMode second) {
        if (first.getWidth() < second.getWidth())
            return -1;
        if (first.getWidth() > second.getWidth())
            return 1;

        if (first.getHeight() < second.getHeight())
            return -1;
        if (first.getHeight() > second.getHeight())
            return 1;

        if (first.getFrequency() < second.getFrequency())
            return -1;
        if (first.getFrequency() > second.getFrequency())
            return 1;

        return 0;
    }
}
