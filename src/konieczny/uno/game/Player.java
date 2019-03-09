package konieczny.uno.game;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import konieczny.uno.cards.*;
import konieczny.uno.cards.comparators.*;
import konieczny.uno.cards.enums.SuperCardType;

public class Player {
    private String name;
    private List<Card> cards;
    private int skips;
    private Comparator<Card> comparator;

    Player(int i, String name){
        if(name.equals(""))
            this.name = "Player" + (i + 1);
        else
            this.name = name;
        this.cards = new ArrayList<Card>();
        this.skips = 0;
    }

    public void setComparator(int x) {
        if(x == 1)
            this.comparator = new CardColorComparator();
        else
            this.comparator = new CardValueComparator();
    }

    public String getName(){
        return this.name;
    }

    public Card getCertainCard(int index){
        return this.cards.get(index);
    }

    public void removeCertainCard(int index){
        this.cards.remove(index);
    }

    public void sortCards (){
        this.cards.sort(this.comparator);
    }

    public void addCard (Card card){
        this.cards.add(card);
    }

    public void writePlayersCards(){
        int numberOfCards = this.cards.size();
        for(int i=0; i<numberOfCards; i++){
            System.out.println("\033[1;30m" + (i + 1) + ". " + this.cards.get(i));
        }
    }

    public int getNumberOfCards(){
        return this.cards.size();
    }

    public List<Card> getCards(){
        return cards;
    }

    public int getSkips(){
        return skips;
    }

    public void decreaseSkips(){
        this.skips--;
    }

    public void setSkips(int skips){
        this.skips = skips;
    }

    public boolean checkIfAddable(Card currentTopCard){
        for(Card card : this.cards){
            if(card.checkIfAddable(currentTopCard))
                return true;
        }
        return false;
    }

    public boolean checkIfThrowable(Card currentTopCard){
        for(Card card : this.cards)
            if(card.checkIfThrowable(currentTopCard))
                return true;
        return false;
    }

    public boolean checkIfSkippable(){
        for(Card card : this.cards)
            if(card instanceof SuperCard && ((SuperCard)card).getType() == SuperCardType.SKIP)
                return true;
        return false;
    }

    public boolean checkIfDefensible(Card currentTopCard){
        for(Card card : this.cards){
            if(card.checkIfThrowableDraw(currentTopCard))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
