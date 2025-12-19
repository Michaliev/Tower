import composites.Helpers;
import screenCapture.Logic;
import screenCapture.ScreenAnalyzer;
import screenCapture.ScreenCapture;
import screenCapture.UI;

import static composites.Helpers.*;
import static screenCapture.ScreenCapture.captureBlueStack;

public class Main {


    public Main() {

        clearFolder("src/main/resources/pictures/archive");
        waiter(2000);
        Logic logic = new Logic();
        while (true) {
            waiter(500);
            logic.mainLoop();
        }
    }

    public static void main(String... args) {
        new Main();
    }


}
