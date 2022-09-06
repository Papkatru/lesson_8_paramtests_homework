package guru.qa;

public enum Lang {
    ENGLISH("ENGLISH"), RUSSIAN("РУССКИЙ");

    private final String description;

    Lang(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
