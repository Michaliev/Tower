package screenCapture;

import composites.Helpers;

import java.awt.*;

import static screenCapture.ScreenCapture.captureBlueStack;

public class Logic {
    ScreenAnalyzer analyzer = new ScreenAnalyzer();
    UI ui;
    Helpers helpers = new Helpers();
    Point offset = new Point(1100, 0);
    Point offset2 = new Point(1325, 0);

    public Logic() {
        this.ui = new UI();
    }

    public void mainLoop() {
        captureBlueStack();

        if (analyzer.isDiamondInArea()) {
            collectDiamond();
        }
        if (ui.getCurrentState().equals(State.RUNNING)) {
            if (analyzer.isHealthLost()) {
                System.out.println("Lost health");
                ui.setState(State.PAUSE);
                pauseGame();
            }
        }
    }

    public void collectDiamond() {
        Point diamondPoint = new Point(13, 450);
        helpers.moveMouseAndClickXTimes(diamondPoint, offset2, 1);
    }

    public void pauseGame() {
        Point pausePoint = new Point(595, 510);
        helpers.moveMouseAndClickXTimes(pausePoint, offset, 30);
    }

    public void resumeGame() {
        Point pausePoint = new Point(695, 510);
        Point offset = new Point(1100, 0);
        helpers.moveMouseAndClickXTimes(pausePoint, offset, 30);
    }
}
