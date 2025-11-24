package NumberIdentification;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class Health {

    private Integer maxHealth = 0;
    private Integer currentHealth = -1;

    private boolean isHealthLetterInEnums(String healthLetter) {
        for (HealthLetters letter : HealthLetters.values()) {
            if (letter.name().equals(healthLetter))
                return true;
        }
        return false;
    }

    private Integer getHealthFromHealthSide(String healthSide) {
        String[] healthSides = getStrings(healthSide);
        if (healthSides == null) {
            return -1;
        }
        String healthLetter = String.valueOf(healthSides[1].charAt(healthSides[1].length() - 1));
        if (!isHealthLetterInEnums(healthLetter)) {
            System.err.println("NO HEALTH");
            return -1;
        }

        HealthLetters healthLetters = HealthLetters.valueOf(healthLetter);
        String healthAddition = healthLetters.getNumber();
        String healthDecimals = healthSides[1].substring(0, healthSides[1].length() - 1);
        String healthString = healthSides[0] + healthDecimals + healthAddition;
        return Integer.valueOf(healthString);
    }

    private String[] getStrings(String healthSide) {
        String[] healthSides = healthSide.replaceAll("\n", "").split("\\.");
        if (healthSides.length != 2) {
            if (healthSides.length != 1 || healthSides[0].length() < 4) {
                System.err.println("Error with health");
                return null;
            }
            StringBuilder whole = new StringBuilder();
//            System.out.println("HEALTH SIZE TOO LOW!");
            for (int i = 0; i < healthSides[0].length() - 3; i++) {
                whole.append(healthSides[0].charAt(i));
            }
            String decimal = healthSides[0].substring(healthSides[0].length() - 3);
            healthSides = new String[]{whole.toString(), decimal};
        }
        return healthSides;
    }

    public void updateHealth(String health) {
        if (health.isEmpty()) {
            maxHealth = 0;
            currentHealth = -1;
            System.err.println("NO HEALTH");
            return;
        }
        String SIDES_DIVIDER = "/";
        String[] healthSides = health.split(SIDES_DIVIDER);
        if (healthSides.length < 2) {
            maxHealth = 0;
            currentHealth = -1;
            System.err.println("NO HEALTH");
            return;
        }
        currentHealth = getHealthFromHealthSide(healthSides[0]);
        maxHealth = getHealthFromHealthSide(healthSides[1]);
    }

    int turn = 0;
    public void printHealth() {
        if (++turn == 10) {
            System.out.println("Current health: " + currentHealth + " / " + maxHealth);
            turn = 0;
        }
    }
}
