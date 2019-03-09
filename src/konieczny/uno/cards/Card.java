package konieczny.uno.cards;
import konieczny.uno.cards.enums.*;

abstract public class Card {

    private Color color;

    public Card (Color color){
        this.color = color;
    }

    public Color getColor(){
        return this.color;
    }

    public boolean checkIfThrowable(Card card){
        if(this.color == Color.WILD)
            return true;
        else if(this.color == card.color)
            return true;
        else if(this instanceof NormalCard && card instanceof NormalCard && ((NormalCard)this).getCardValue() == ((NormalCard)card).getCardValue())
            return true;
        else if(this instanceof SuperCard && card instanceof SuperCard && ((SuperCard)this).getType() == ((SuperCard)card).getType())
            return true;
        return false;
    }

    public boolean checkIfAddable(Card card){
        if(this instanceof NormalCard && card instanceof NormalCard && ((NormalCard)this).getCardValue() == ((NormalCard)card).getCardValue())
            return true;
        if(this instanceof SuperCard && card instanceof SuperCard && ((SuperCard)this).getType() == ((SuperCard)card).getType())
            return true;
        return false;
    }

    public boolean checkIfThrowableDraw(Card currentTopCard){
        if(this.checkIfThrowable(currentTopCard))
            if(this instanceof SuperCard && (((SuperCard)this).getType() == SuperCardType.DRAW2 || ((SuperCard)this).getType() == SuperCardType.DRAW4))
                return true;
        return false;
    }

    @Override
    public String toString(){
        if(this instanceof NormalCard)
            return (this.color + " " + this.color.getFont() + ((NormalCard)this).getCardValue() + "\033[0m");
        else
            return (this.color + " " + this.color.getFont() + ((SuperCard)this).getType() + "\033[0m");
    }
}
