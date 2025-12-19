package NumberIdentification;

import lombok.Getter;
import lombok.Setter;

import static composites.Helpers.doesStringContainsLetter;
import static composites.Helpers.saveExistingFile;

@Getter
@Setter
public class Health {

    private Integer maxHealth = 0;
    private Integer thresholdPackageHealth = 0;
    private Integer currentHealth = -1;
    private int turnHealthPrint = 10;

    private boolean isHealthLetterInEnums(String healthLetter) {
        for (HealthLetters letter : HealthLetters.values()) {
            if (letter.name().equals(healthLetter))
                return true;
        }
        return false;
    }

    private String removeAnyLetters(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }

    private Integer getHealthFromHealthSide(String healthSide) {

        String[] healthSides = getStrings(healthSide);
        if (healthSides == null) {
            throwError(healthSide);
            return -1;
        }
        String leftSide = healthSides[0];
        String rightSide = healthSides[1];
        String healthLetter = String.valueOf(rightSide.charAt(rightSide.length() - 1));
        rightSide = removeAnyLetters(rightSide);
        if (!isHealthLetterInEnums(healthLetter)) {
            throwError(healthSide);
            return -1;
        }

        HealthLetters healthLetters = HealthLetters.valueOf(healthLetter);
        String healthAddition = healthLetters.getNumber();
        String healthString = leftSide + rightSide + healthAddition;
        if (doesStringContainsLetter(healthString)) {
            throwError(healthString, "healhtString contain letter! ");
            return -1;
        }
        return Integer.valueOf(healthString);
    }

    private String[] getStrings(String healthSide) {
        String[] healthSides = healthSide.replaceAll("\n", "").split("\\.");
        if (healthSides.length != 2) {
            if (healthSides.length != 1 || healthSides[0].length() < 4) {
                throwError(healthSide);
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

    int errCount = 0;

    private void throwError(String health) {
        throwError(health, "");
    }

    private void throwError(String health, String optionalString) {
        maxHealth = 0;
        currentHealth = 0;
        String healthErr = health + errCount++;
        System.err.println(healthErr + " " + optionalString);
    }

    public void updateHealth(String health) {
        if (health.isEmpty()) {
            throwError(health);
            return;
        }
        String SIDES_DIVIDER = "/";
        String[] healthSides = health.split(SIDES_DIVIDER);
        if (healthSides.length < 2) {
            throwError(health);
            return;
        }
        currentHealth = getHealthFromHealthSide(healthSides[0]);
        maxHealth = getHealthFromHealthSide(healthSides[1]);
    }

    int turn = turnHealthPrint;

    public void printHealth() {
        if (++turn >= turnHealthPrint) {
            System.out.println("Current health: " + currentHealth + " / " + maxHealth);
            turn = 0;
        }
    }
}
