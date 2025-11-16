package screenCapture;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

import static composites.Helpers.waiter;
import static screenCapture.ScreenCapture.captureBlueStack;
import static screenCapture.State.PAUSE;
import static screenCapture.State.RUNNING;

public class UI extends JFrame {
    final JButton button = new JButton("Start");
    @Getter
    @Setter
    State currentState = PAUSE;

    public UI() {
        setTitle("Tower");
        setSize(250, 100);
        Point location = new Point(1100, 350);
        setLocation(location);

        setVisible(true);
        button.addActionListener(e -> mainButtonLogic());
        JPanel panel = new JPanel();
        panel.add(button);
        setAlwaysOnTop(true);
        this.getContentPane().add(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setState(State state) {
        switch (state) {
            case PAUSE:
                button.setText("Pause");
                currentState = PAUSE;
                break;
            case RUNNING:
                button.setText("Running");
                currentState = RUNNING;
                break;
            default:
                button.setText("ERROR");
                currentState = State.QUIT;

        }
    }

    private void mainButtonLogic() {
        if (currentState.equals(PAUSE)) {
            setState(RUNNING);
        } else {
            setState(PAUSE);
        }
    }

}
