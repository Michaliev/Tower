package composites;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import static screenCapture.Helper.resizeImage;

public class Helpers {
    final static String PICTURES_PATH = "src/main/resources/pictures/";
    Robot robot;

    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }


    public static void waiter(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void changeSizeOfTemplate(String name, Rectangle rectangle) {
        String path = "src/main/resources/templates/" + name;
        BufferedImage image = readImage(path);
        saveFile(resizeImage(image, new Rectangle(rectangle.width, rectangle.height)), new StringBuilder(path));
    }

    public static void saveFile(BufferedImage image, StringBuilder path) {
        File outputfile = new File(path.toString());
        try {
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeJPG(
            BufferedImage bufferedImage,
            OutputStream outputStream,
            float quality) throws IOException {
        Iterator<ImageWriter> iterator =
                ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter imageWriter = iterator.next();
        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(quality);
        ImageOutputStream imageOutputStream =
                new MemoryCacheImageOutputStream(outputStream);
        imageWriter.setOutput(imageOutputStream);
        IIOImage iioimage = new IIOImage(bufferedImage, null, null);
        imageWriter.write(null, iioimage, imageWriteParam);
        imageOutputStream.flush();
    }

    public static void saveFile(BufferedImage image, String name, String localPath) {
        if (!new File(PICTURES_PATH).exists())
            new File(PICTURES_PATH).mkdirs();

        if (!new File(PICTURES_PATH + localPath).exists())
            new File(PICTURES_PATH + localPath).mkdirs();

        File outputfile = new File(PICTURES_PATH + localPath + "/" + name + ".png");
        try {
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveFile(BufferedImage image, String name) {
        saveFile(image, name, "");
    }

    @SneakyThrows
    public static void deleteFolder(String path) {
        FileUtils.deleteDirectory(new File(path));
    }

    public static void clearFolder(String path) {
        deleteFolder(path);
        createFolder(path);
    }

    public static void createFolder(String path) {
        new File(path).mkdirs();
    }

    public static void deletePictures() {
        deleteFolder("src/main/resources/pictures");
    }

    public void moveMouseAndClick(Point position, Point offset) {
        moveMouseAndClickXTimes(position, offset, 1);
    }

    public void moveMouseAndClickXTimes(Point position, Point offset, int clicks) {
        int wait = 10;
        if (position == null) {
            System.err.println("NULL POSITION");
            return;
        }
        int inputEvent = InputEvent.BUTTON1_DOWN_MASK;
        robot.mouseMove(position.x + offset.x, position.y + offset.y);
        for (int i = 0; i < clicks; i++) {
            waiter(wait);
            robot.mousePress(inputEvent);
            waiter(wait);
            robot.mouseRelease(inputEvent);
        }
    }

    @SneakyThrows
    public static BufferedImage readImage(String path) {
        return ImageIO.read(new File(path));
    }

    public static BufferedImage readMain() {
        return readImage(PICTURES_PATH + "main.png");
    }
}
