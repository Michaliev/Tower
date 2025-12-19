package screenCapture;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;

import static composites.Helpers.saveExistingFile;
import static composites.Helpers.saveFile;
import static screenCapture.Helper.resizeImage;
import static screenCapture.WindowFinder.getWindowInfo;

@Getter
@Setter
public class ScreenCapture {

    private static boolean resize = false;
    public static String writePath = "src/main/resources/pictures/";
    public static String archivePathString = writePath + "archive";
    static Path archivePath = Paths.get(archivePathString);
    public static String MAIN = "main";
    public static Rectangle resizeRectangle = new Rectangle(1920, 1080);

    public static void main(String... args) {
        captureGivenAppWindow("BlueStacks App Player");
    }

    public static boolean captureBlueStack() {
        return captureGivenAppWindow("BlueStacks App Player");
    }

    static int turn = 0;

    @SneakyThrows
    public static boolean captureGivenAppWindow(String appName) {
//        checkWindowOfName(appName);
        int hWnd = WindowFinder.User32.instance.FindWindowA(null, appName);
        WindowFinder.WindowInfo w = getWindowInfo(hWnd);
//        WindowFinder.User32.instance.SetForegroundWindow(w.hwnd);

        // turned off - if on window keeps geting focus, consider doing it only once on start
        BufferedImage screenCapture = new Robot().createScreenCapture(new Rectangle(w.rect.left, w.rect.top, w.rect.right - w.rect.left, w.rect.bottom - w.rect.top));
        if (resize) screenCapture = resizeImage(screenCapture, resizeRectangle);

        saveFile(screenCapture, MAIN);
        return true;
    }


}
