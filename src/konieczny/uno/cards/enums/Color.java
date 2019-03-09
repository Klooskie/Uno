package konieczny.uno.cards.enums;

public enum Color {
    WILD(0, "\033[1;30m", "Black"),
    RED(1, "\033[1;31m", "Red"),
    GREEN(2, "\033[1;32m", "Green"),
    BLUE(3, "\033[1;34m", "Blue"),
    YELLOW(4, "\033[1;33m", "Yellow");

    private int colorID;
    private String font;
    private String colorName;

    private Color(int colorID, String font, String colorName) {
        this.colorID = colorID;
        this.font = font;
        this.colorName = colorName;
    }

    public static Color getColorByID(int colorID) {
        for (Color color : Color.values())
            if (color.colorID == colorID)
                return color;
        return null;
    }

    public int getColorID() {
        return this.colorID;
    }

    public String getFont() {
        return this.font;
    }

    public static void writeColors() {
        System.out.println("Możliwe kolory to:\n\033[1;30m1. " + Color.RED + "\n\033[1;30m2. " + Color.GREEN + "\n\033[1;30m3. " + Color.BLUE + "\n\033[1;30m4. " + Color.YELLOW);
    }

    @Override
    public String toString() {
        return this.font + this.colorName + "\033[0m";
    }
}
