package NumberIdentification;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import static composites.Helpers.saveFile;
import static screenCapture.Helper.resizeImage;


public class NumberIdentification {

    public String getStringFromImage(BufferedImage image, String string) {
        final ITesseract instance = new Tesseract();
//        instance.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
        instance.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata_best-main");
        instance.setVariable("user_defined_dpi", "70");
        // Source and destination are the same.
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

    public String getHealthFromImage(BufferedImage image) {
        final ITesseract instance = new Tesseract();
//        instance.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
        instance.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata_best-main");
        instance.setVariable("user_defined_dpi", "70");
        // Source and destination are the same.
        instance.setVariable("tessedit_char_whitelist", "0123456789/.KMB");
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

    public static void changeColorOfAnyOtherPixel(BufferedImage elementImage, Color elementColor, Color replacement) {
        for (int j = 0; j < elementImage.getWidth(); j++)
            for (int k = 0; k < elementImage.getHeight(); k++) {
                if (elementImage.getRGB(j, k) != elementColor.getRGB()) {
                    elementImage.setRGB(j, k, replacement.getRGB());
                }
            }
    }
}
