package NumberIdentification;

import lombok.Getter;

import java.util.HashSet;

public enum HealthLetters {

    K("0"),
    M("0000");
    private String number;

    @Getter
    public HashSet<HealthLetters> enumSet = new HashSet<>();

    HealthLetters(String number) {
        this.number = number;
        enumSet.add(this);
    }

    String getNumber() {
        return number;
    }
}
