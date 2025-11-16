package screenCapture;

import composites.Helpers;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashSet;

import static composites.Helpers.readMain;
import static composites.Helpers.saveFile;

public class ScreenAnalyzer {
    Helpers helpers = new Helpers();

    public BufferedImage getHealthBarImage(BufferedImage main) {
//        Point healthBarStart = new Point(340, 580); - if ad is running
        Point healthBarStart = new Point(20, 585);
        Dimension rectangleSize = new Dimension(240, 25);
        return getImageAndSave(main, healthBarStart, rectangleSize, "healthBar");
    }

    public BufferedImage getDiamondAreaImage(BufferedImage main) {
        Point diamondStart = new Point(13, 450);
        Dimension rectangleSize = new Dimension(125, 75);
        return getImageAndSave(main, diamondStart, rectangleSize, "diamondArea");
    }

    private BufferedImage getImageAndSave(BufferedImage main, Point startPoint, Dimension size, String name) {
        Rectangle rectangleSize = new Rectangle(startPoint, size);
        BufferedImage area = getSubimage(main, rectangleSize);
        saveFile(area, name);
        return area;
    }

    public BufferedImage getSubimage(BufferedImage bufferedImage, Rectangle rectangle) {
        return bufferedImage.getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public boolean isHealthLost() {
        //to proper health analyze you need to capture Main before this function

        int healthInt = -15743074;
        int healthInt2 = -15669578;
        HashSet<Integer> healthColors = new HashSet<>(Arrays.asList(healthInt, healthInt2));
        BufferedImage healthBar = getHealthBarImage(readMain());

        Point analyzePoint = new Point(190, 0);
        Dimension analyzeDimension = new Dimension(2, 25);
        BufferedImage healthSlice = getImageAndSave(healthBar, analyzePoint, analyzeDimension, "healthSlice");
        int analyzePixel = healthSlice.getRGB(0, 0);
        return !healthColors.contains(analyzePixel);
    }

    public boolean isDiamondInArea() {
        int diamondInt = -5875022;
        int diamondInt2 = -3837495;
        int diamondInd3=-9556601;
        HashSet<Integer> purpleColors = new HashSet<>(Arrays.asList(diamondInt, diamondInt2,diamondInd3));
        BufferedImage diamondArea = getDiamondAreaImage(readMain());

        int purplePixelCount = 0;
        int w = diamondArea.getWidth();
        int h = diamondArea.getHeight();
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++) {
                int currentRgb = diamondArea.getRGB(i, j);
                if (purpleColors.contains(currentRgb))
                    purplePixelCount++;
            }
        System.out.println("Pixel count is "+purplePixelCount);
        return purplePixelCount > 5;
    }

}
