package com.netease.tools.abtestuicreator.util;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class ColorUtil {

    public static String ColorToHex(int color) {
        int a = android.graphics.Color.alpha(color);
        int b = android.graphics.Color.blue(color);
        int g = android.graphics.Color.green(color);
        int r = android.graphics.Color.red(color);

        String alphaHex = To00Hex(a);
        String blueHex = To00Hex(b);
        String greenHex = To00Hex(g);
        String redHex = To00Hex(r);

        // hexBinary value: aabbggrr
        StringBuilder str = new StringBuilder("#");
        str.append(alphaHex);
        str.append(blueHex);
        str.append(greenHex);
        str.append(redHex );

        return str.toString();
    }

    private static String To00Hex(int value) {
        String hex = "00".concat(Integer.toHexString(value));
        return hex.substring(hex.length()-2, hex.length());
    }
}
