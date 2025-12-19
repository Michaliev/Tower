package screenCapture;

import NumberIdentification.Health;
import javafx.scene.text.FontWeight;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

import static screenCapture.State.PAUSED;
import static screenCapture.State.RUNNING;

public class UI extends JFrame {
    final JButton button = new JButton("Start");
    Health health;
    final JButton healthChanger = new JButton("HP");
    JTextField healthTextfield;
    final int HP_INIT = 80;
    JSlider slider;
    @Getter
    @Setter
    State currentState = PAUSED;
    int healthThreshold = 0;


    private void setupSlider() {
        final int HP_MIN = 0;
        final int HP_MAX = 100;
        healthThreshold = HP_INIT;
        slider = new JSlider(JSlider.HORIZONTAL, HP_MIN, HP_MAX, HP_INIT);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setName("HEAALTH");
        Font font = new Font("Serif", Font.ITALIC, 15);
        slider.setFont(font);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(this::stateChanged);

    }

    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            healthThreshold = (int) source.getValue();
        }
    }

    public boolean isPackageThreshold() {
        return healthChanger.getText().equals("PCKG");
    }


    @SneakyThrows
    public UI(Health health) {
        this.health=health;
        setTitle("Tower");

        Point location = new Point(1100, 350);
        setLocation(location);
        setupSlider();

        setSize(335, 200);
        button.addActionListener(e -> mainButtonLogic());
        healthChanger.addActionListener(e -> {
            if (healthChanger.getText().equals("HP")) {
                healthChanger.setText("PCKG");
                health.setThresholdPackageHealth(health.getCurrentHealth());
            } else healthChanger.setText("HP");
        });
        JPanel panel = new JPanel();
        panel.add(button);
        slider.setToolTipText("Set health percentage to stop");
        healthTextfield = new JTextField("Health will stop at " + HP_INIT + " percent");
        healthTextfield.setFont(new Font("Serif", Font.BOLD, 20));
        slider.addChangeListener(e -> {
            healthTextfield.setText("Health will stop at " + slider.getValue() + " percent");
        });
        panel.add(healthTextfield);
        panel.add(slider);
        panel.add(healthChanger);
        setAlwaysOnTop(true);
        this.getContentPane().add(panel);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public int getHeathPausePercentValue() {
        return slider.getValue();
    }

    public void setHealthText(String health) {
        healthTextfield.setText(health);
    }

    public void setState(State state) {
        switch (state) {
            case PAUSED:
                button.setText(PAUSED.name());
                currentState = PAUSED;
                break;
            case RUNNING:
                button.setText(RUNNING.name());
                currentState = RUNNING;
                break;
            default:
                button.setText("ERROR");
                currentState = State.QUIT;

        }
    }

    private void mainButtonLogic() {
        if (currentState.equals(PAUSED)) {
            setState(RUNNING);
        } else {
            setState(PAUSED);
        }
    }

}
