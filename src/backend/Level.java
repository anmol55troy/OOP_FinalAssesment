package backend;

public enum Level {
    BEGINNER("Beginner"), 
    INTERMEDIATE("Intermediate"), 
    ADVANCED("Advanced");

    private final String displayName;

    Level(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static Level fromString(String text) {
        for (Level level : Level.values()) {
            if (level.displayName.equalsIgnoreCase(text)) {
                return level;
            }
        }
        return BEGINNER; // Default
    }
}
