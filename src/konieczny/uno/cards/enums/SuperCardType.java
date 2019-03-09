package konieczny.uno.cards.enums;

public enum SuperCardType {
    SKIP(1, "Stop"),
    REVERSE(2, "Zmiana kierunku"),
    DRAW2(3, "Plus2"),
    DRAW4(4, "Plus4"),
    COLORCHANGE(5, "Zmiana koloru");

    private int superCardTypeID;
    private String typeName;

    private SuperCardType(int superCardTypeID, String typeName) {
        this.superCardTypeID = superCardTypeID;
        this.typeName = typeName;
    }

    public int getTypeID() {
        return this.superCardTypeID;
    }

    @Override
    public String toString() {
        return this.typeName;
    }
}
