package konieczny.uno.cards;
import konieczny.uno.cards.enums.Color;

public class NormalCard extends Card {
    private int cardValue;

    public NormalCard(Color color, int cardValue){
        super(color);
        this.cardValue = cardValue;
    }

    public int getCardValue(){
        return this.cardValue;
    }
}
