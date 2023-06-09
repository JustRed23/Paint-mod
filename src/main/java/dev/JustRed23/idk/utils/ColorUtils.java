package dev.JustRed23.idk.utils;

public final class ColorUtils {

    public static String rgbToHex(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }

    public static int mixColors(int color, int color2) {
        int r = (color >> 16 & 0xFF) + (color2 >> 16 & 0xFF);
        int g = (color >> 8 & 0xFF) + (color2 >> 8 & 0xFF);
        int b = (color & 0xFF) + (color2 & 0xFF);
        return (r / 2) << 16 | (g / 2) << 8 | (b / 2);
    }
}
