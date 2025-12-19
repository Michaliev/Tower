package NumberIdentification;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.Arrays;
import java.util.HashSet;


import static composites.Helpers.saveFile;
import static screenCapture.Helper.resizeImage;


public class NumberIdentification {

    public String getStringFromImage(BufferedImage image, String string) {
        final ITesseract instance = new Tesseract();
//        instance.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
        instance.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata_best-main");
        instance.setVariable("user_defined_dpi", "300");
        instance.setVariable("tessedit_char_whitelist", string);
        String result;
        try {
            result = instance.doOCR(image);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
//        result = result.replaceAll("\\s+", "");
        try {
            return result;
        } catch (NumberFormatException e) {
            System.err.println("ERROR NUMBER IS NON-NUMERIC  RESULT IS --- " + result);
            return null;
        }
    }


    private static boolean isWhite(Color color) {
        int whiteLimit = 200;
        if (color.getGreen() < whiteLimit)
            return false;
        if (color.getBlue() < whiteLimit)
            return false;
        return color.getRed() >= whiteLimit;
    }


    public String getHealthFromImage(BufferedImage image) {
        final ITesseract instance = new Tesseract();
        instance.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
//        instance.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata_best-main");
        instance.setVariable("user_defined_dpi", "300");
        instance.setVariable("tessedit_char_whitelist", "0123456789/.KMB");
//        image=changeColorOfGivenPixel(image,);
        image = resizeImage(image, 3);
        changeColorOfNonWhitePixels(image);
        saveFile(image, "changedImage");
        String result;
        try {
            result = instance.doOCR(image);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
//        result = result.replaceAll("\\s+", "");
        try {
            return result;
        } catch (NumberFormatException e) {
            System.err.println("ERROR NUMBER IS NON-NUMERIC  RESULT IS --- " + result);
            return null;
        }
    }

    public static void blackenNonWhitePixels(BufferedImage elementImage) {
        changeColorOfAnyOtherPixel(elementImage, Color.WHITE, Color.BLACK);
    }

    public static void changeColorOfGivenPixel(BufferedImage elementImage, Color elementColor, Color replacement) {
        for (int j = 0; j < elementImage.getWidth(); j++)
            for (int k = 0; k < elementImage.getHeight(); k++) {
                if (elementImage.getRGB(j, k) == elementColor.getRGB()) {
                    elementImage.setRGB(j, k, replacement.getRGB());
                }
            }
    }

    public static void changeColorOfNonWhitePixels(BufferedImage elementImage) {
        for (int j = 0; j < elementImage.getWidth(); j++)
            for (int k = 0; k < elementImage.getHeight(); k++) {
                if (!isWhite(new Color(elementImage.getRGB(j, k)))) {
                    elementImage.setRGB(j, k, Color.BLACK.getRGB());
                }
            }
    }

    public static void changeColorOfAnyOtherPixel(BufferedImage elementImage, HashSet<Color> keepColors, Color replacement) {
        for (int j = 0; j < elementImage.getWidth(); j++)
            for (int k = 0; k < elementImage.getHeight(); k++) {
                if (!keepColors.contains(new Color(elementImage.getRGB(j, k)))) {
                    elementImage.setRGB(j, k, replacement.getRGB());
                }
            }
    }

    public static void changeColorOfAnyOtherPixel(BufferedImage elementImage, Color elementColor, Color replacement) {
        HashSet<Color> set = new HashSet<>();
        set.add(elementColor);
        changeColorOfAnyOtherPixel(elementImage, set, replacement);
    }
}
