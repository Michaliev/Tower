package screenCapture;

import composites.Helpers;
import NumberIdentification.Health;

import java.awt.*;

import static composites.Helpers.*;
import static screenCapture.ScreenCapture.*;

public class Logic {
    Health health = new Health();
    ScreenAnalyzer analyzer = new ScreenAnalyzer(health);
    UI ui;
    Helpers helpers = new Helpers();
    Point offset = new Point(1100, 0);
    Point offset2 = new Point(1325, 0);

    public Logic() {
        this.ui = new UI(health);
    }

    int turn = -1;

    public void mainLoop() {
        captureBlueStack();

        if (analyzer.isDiamondInArea()) {
            collectDiamond();
        }
        if (ui.getCurrentState().equals(State.RUNNING)) {
            if (++turn > 0) {
                saveExistingFile(MAIN, String.valueOf(turn), archivePath);
                if (turn >= 200)
                    turn = 0;
            }
            if (analyzer.isHealthLost(ui)) {
                System.out.println("Lost health at " + turn);
                String newName = "lostHealth " + health.getCurrentHealth() + "max " + health.getMaxHealth();
                saveExistingFile("healthBar", newName);
                ui.setState(State.PAUSED);
                pauseGame();
            }
        }

    }

    public void collectDiamond() {
        System.out.println("COLLECTED DIAMOND");
        Point diamondPoint = new Point(310, 520);
        helpers.moveMouseAndClickXTimes(diamondPoint, offset, 1);
    }

    public void pauseGame() {
        Point pausePoint = new Point(620, 510);
        helpers.moveMouseAndClickXTimes(pausePoint, offset, 30);
    }

    public void resumeGame() {
        Point pausePoint = new Point(695, 510);
        Point offset = new Point(1100, 0);
        helpers.moveMouseAndClickXTimes(pausePoint, offset, 30);
    }
}
