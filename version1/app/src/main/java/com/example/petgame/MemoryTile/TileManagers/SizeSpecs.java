package com.example.petgame.MemoryTile.TileManagers;

import java.util.ArrayList;

/**
 * Stores information for each size of the
 */
public enum SizeSpecs {
    SMALL("Small",3, 5, 4, 5, 5),
    MEDIUM("Medium",5, 8, 5, 10, 10),
    LARGE("Large",5, 9, 6, 12, 20);

    public final String label;
    public final int lives;
    public final int height;
    public final int width;
    public final int moneyNum;
    public final int payMulti;

    SizeSpecs(String label, int lives, int height, int width, int moneyNum, int payMulti) {
        this.label = label;
        this.lives = lives;
        this.height = height;
        this.width = width;
        this.moneyNum = moneyNum;
        this.payMulti = payMulti;
    }

    @Override
    public String toString() {
        return this.label;
    }

    /**
     * Returns the SizeSpecs which matches the given string.
     * @param label a string that should match the label of some SizeSpecs.
     * @return the SizeSpecs that matches label; null if none matches.
     */
    public static SizeSpecs valueOfLabel(String label) {
        for (SizeSpecs e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }

    public static String[] labels() {
        ArrayList<String> labels = new ArrayList<>();
        int size = 0;
        for (SizeSpecs s : values()) {
            labels.add(s.label);
            size++;
        }
        return labels.toArray(new String[size]);
    }
}
