package konieczny.uno.game;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import konieczny.uno.cards.*;
import konieczny.uno.cards.enums.Color;

public class DeckAndTable {
    private Queue<Card> deck;
    private List<Card> usedCards;
    private Card currentTopCard;
    private boolean direction; //kierunek moze sie zmieniac po wyrzuceniu karty REVERSE
    private int skipsCounted; //naliczone 'kolejki'
    private int cardsCounted; //naliczone karty do pobrania


    DeckAndTable (){
        this.deck = new LinkedList<Card>();
        this.usedCards = new LinkedList<Card>();
        this.direction = true;
        this.skipsCounted = 0;
        this.cardsCounted = 0;
    }

    public void add(Card card){
        this.deck.add(card);
    }

    public Card element(){
        return this.deck.element();
    }

    public Card remove(){
        return this.deck.remove();
    }

    public void shuffleDeck(){
        Collections.shuffle((LinkedList)this.deck);
    }

    public void usedCardAdd(Card card){
        this.usedCards.add(card);
    }

    public void topToCurrent(){
        this.currentTopCard = this.deck.element();
        this.usedCards.add(this.deck.remove());
    }

    public Card getCurrentTopCard(){
        return currentTopCard;
    }

    public void setCurrentTopCard(Card card){
        this.currentTopCard = card;
    }

    public void repairIfEmpty(){
        if(this.deck.isEmpty()) {
            Collections.shuffle(this.usedCards);
            if (this.deck.addAll(usedCards))
                this.usedCards.clear();
            else{
                System.out.println("Karty się skończyły, koniec gry");
                System.exit(0);
            }
        }
    }

    public boolean getDirection(){
        return this.direction;
    }

    public void negateDirection(){
        this.direction = !this.direction;
    }

    public int getSkipsCounted(){
        return this.skipsCounted;
    }

    public void increaseSkipsCounted(){
        this.skipsCounted++;
    }

    public void zeroSkipsCounted(){
        this.skipsCounted = 0;
    }

    public int getCardsCounted(){
        return this.cardsCounted;
    }

    public void increaseCardsCounted(int x){
        this.cardsCounted += x;
    }

    public void zeroCardsCounted(){
        this.cardsCounted = 0;
    }

    public void changeColor(Color color){
        this.currentTopCard = new SuperCard(color, ((SuperCard)this.currentTopCard).getType());
    }
}
