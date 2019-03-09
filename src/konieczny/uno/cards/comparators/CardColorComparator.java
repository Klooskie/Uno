package konieczny.uno.cards.comparators;
import java.util.Comparator;
import konieczny.uno.cards.*;

public class CardColorComparator implements Comparator<Card> {

    public int compare(Card c1, Card c2){
        if(c1.getColor() == c2.getColor()) {
            if(c1 instanceof NormalCard && c2 instanceof NormalCard)
                return ((NormalCard)c1).getCardValue() - ((NormalCard)c2).getCardValue();
            else if(c1 instanceof SuperCard && c2 instanceof SuperCard)
                return ((SuperCard)c1).getType().getTypeID() - ((SuperCard)c2).getType().getTypeID();
            else if(c1 instanceof NormalCard && c2 instanceof SuperCard)
                return -1;
            else
                return 1;
        } else {
            return c1.getColor().getColorID() - c2.getColor().getColorID();
        }
    }
}
