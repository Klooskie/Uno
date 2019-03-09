package konieczny.uno.cards;
import konieczny.uno.cards.enums.*;

public class SuperCard extends Card {
    private SuperCardType type;

    public SuperCard(Color color, SuperCardType type){
        super(color);
        this.type = type;
    }

    public SuperCardType getType(){
        return this.type;
    }

}
