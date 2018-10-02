import javafx.scene.paint.Color;

public class ColorUtils {

    public static String rgbToHex(int r, int g, int b){
        r = confineRgbVal255Max(r);
        g = confineRgbVal255Max(g);
        b = confineRgbVal255Max(b);

        return String.join("", "#",
                String.format("%02X", r),
                String.format("%02X", g),
                String.format("%02X", b));
    }

    public static int confineRgbVal255Max(int val){
        if (val < 0) {val = 0;}
        if (val > 255) {val = 255;}
        return val;
    }

    public static double confineRgbVal1Max(double val){
        if (val < 0) {val = 0;}
        if (val > 1) {val = 1;}
        return val;
    }

    public static double convertRgb255ToRatio(int val){
        return val / 255.;
    }

    public static Color asFxColor255Max(int r, int g, int b, int a){
        double rAsDec = convertRgb255ToRatio(confineRgbVal255Max(r));
        double gAsDec = convertRgb255ToRatio(confineRgbVal255Max(g));
        double bAsDec = convertRgb255ToRatio(confineRgbVal255Max(b));
        double aAsDec = convertRgb255ToRatio(confineRgbVal255Max(a));
        return new Color(rAsDec, gAsDec, bAsDec, aAsDec);
    }

    public static Color asFxColor1Max(double r, double g, double b, double a){
        r = confineRgbVal1Max(r);
        g = confineRgbVal1Max(g);
        b = confineRgbVal1Max(b);
        a = confineRgbVal1Max(a);
        return new Color(r, g, b, a);
    }

    public static Color asFxColorHex(String colorHex) throws ColorFormatException{

        colorHex = colorHex.charAt(0) == '#' ? colorHex.substring(1) : colorHex;

        if(colorHex.length() != 8) {
            throw new ColorFormatException("Invalid hex color value. Must include two hex values for r, g, b, a. " +
                    "It must also range from #00000000 to #FFFFFFFF");
        }
        int r  = Integer.parseInt(colorHex.substring(0, 2), 16);
        int g  = Integer.parseInt(colorHex.substring(2, 4), 16);
        int b  = Integer.parseInt(colorHex.substring(4, 6), 16);
        int a  = Integer.parseInt(colorHex.substring(6, 8), 16);
        return asFxColor255Max(r, g, b, a);
    }
}
