package screenCapture;

import NumberIdentification.NumberIdentification;
import composites.Helpers;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashSet;

import NumberIdentification.Health;

import static composites.Helpers.readMain;
import static composites.Helpers.saveFile;

public class ScreenAnalyzer {
    Helpers helpers = new Helpers();
    Health health;

    ScreenAnalyzer(Health health) {
        this.health = health;
    }

    public BufferedImage getHealthBarImage(BufferedImage main) {
//        Point healthBarStart = new Point(340, 580); - if ad is running
        Point healthBarStart = new Point(20, 580);
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
        BufferedImage healthBar = getHealthBarImage(readMain());
        String healthString = new NumberIdentification().getHealthFromImage(healthBar);
        health.updateHealth(healthString);
        health.printHealth();
        return health.getCurrentHealth() < health.getMaxHealth();

    }

    public boolean isDiamondInArea() {
        BufferedImage diamondArea = getDiamondAreaImage(readMain());
        String result = new NumberIdentification().getStringFromImage(diamondArea, "CLAIM");
        return result.toUpperCase().contains("CLAIM");
    }

}
