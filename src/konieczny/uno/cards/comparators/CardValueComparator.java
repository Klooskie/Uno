package konieczny.uno.cards.comparators;
import java.util.Comparator;
import konieczny.uno.cards.*;

public class CardValueComparator implements Comparator<Card>{

    public int compare(Card c1, Card c2) {
        if (c1 instanceof NormalCard && c2 instanceof NormalCard) {
            if (((NormalCard) c1).getCardValue() == ((NormalCard) c2).getCardValue())
                return c1.getColor().getColorID() - c2.getColor().getColorID();
            else
                return ((NormalCard) c1).getCardValue() - ((NormalCard) c2).getCardValue();
        }
        if (c1 instanceof SuperCard && c2 instanceof SuperCard) {
            if (((SuperCard) c1).getType() == ((SuperCard) c2).getType())
                return c1.getColor().getColorID() - c2.getColor().getColorID();
            else
                return ((SuperCard) c1).getType().getTypeID() - ((SuperCard) c2).getType().getTypeID();
        }
        if (c1 instanceof NormalCard && c2 instanceof SuperCard)
            return 1;
        return -1;
    }

}
