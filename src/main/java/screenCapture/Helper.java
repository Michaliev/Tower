package screenCapture;

import java.awt.*;
import java.awt.image.BufferedImage;

import static composites.Helpers.readImage;

public class Helper {
    public static void blackenNonWhitePixels(BufferedImage elementImage) {
        changeGivenColors(elementImage, Color.WHITE, Color.BLACK);
    }

    public static void changeGivenColors(BufferedImage elementImage, Color elementColor, Color replacement) {
        for (int j = 0; j < elementImage.getWidth(); j++)
            for (int k = 0; k < elementImage.getHeight(); k++)
                if (elementImage.getRGB(j, k) != elementColor.getRGB()) {
                    elementImage.setRGB(j, k, replacement.getRGB());
                }
    }

    public static void paintRectangle(BufferedImage image, Rectangle rectangle) {
        Graphics2D graph = image.createGraphics();
        graph.setColor(Color.BLUE);
        graph.fill(new Rectangle(rectangle.x, rectangle.y, (int) rectangle.getWidth(), (int) rectangle.getHeight()));
        graph.dispose();
    }

    public static BufferedImage resizeQuality(BufferedImage bufferedImage, Dimension dimension, boolean scale) {
        javaxt.io.Image image = new javaxt.io.Image(bufferedImage);

        if (scale) {
            Dimension scaledDimension = getScaledDimension(new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight()), new Dimension(dimension.width, dimension.height));
            dimension = new Dimension(scaledDimension.width, scaledDimension.height);
        }
        image.setWidth(dimension.width);
        image.setHeight(dimension.height);
        image.setOutputQuality(1);

        return image.getBufferedImage();
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, Rectangle targetRectangle, boolean resize) {
        if (resize) {
            Dimension scale = getScaledDimension(new Dimension(originalImage.getWidth(), originalImage.getHeight()), new Dimension(targetRectangle.width, targetRectangle.height));
            targetRectangle = new Rectangle(scale.width, scale.height);
        }
        Image resultingImage = originalImage.getScaledInstance(targetRectangle.width, targetRectangle.height, Image.SCALE_AREA_AVERAGING);
        BufferedImage outputImage = new BufferedImage(targetRectangle.width, targetRectangle.height, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);

        return outputImage;
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, Rectangle targetRectangle) {
        return resizeImage(originalImage, targetRectangle, false);
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int multiplier) {
        Rectangle rectangle = new Rectangle(originalImage.getWidth() * multiplier, originalImage.getHeight() * multiplier);
        return resizeImage(originalImage, rectangle, true);
    }
    public static BufferedImage resizeImage(String imagePath, Rectangle targetRectangle) {
        return resizeImage(readImage(imagePath), targetRectangle);
    }

    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }
}
