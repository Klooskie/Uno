package konieczny.uno.game.moves;
import konieczny.uno.ConsoleReader;
import konieczny.uno.cards.*;
import konieczny.uno.cards.enums.SuperCardType;
import konieczny.uno.game.DeckAndTable;
import konieczny.uno.game.Player;

public class MoveDraw extends Move {

    public MoveDraw(Player player, DeckAndTable deckAndTable){
        super(player, deckAndTable);
    }

    public void makeMoveDraw(){
        System.out.println("\033[0;35mNaliczona liczba kart do pobrania wynosi: " + this.deckAndTable.getCardsCounted() +  "\033[0m");
        if(player.checkIfDefensible(this.deckAndTable.getCurrentTopCard())){
            ConsoleReader reader = new ConsoleReader();
            int index = reader.readIndexOfCard("podebrać naliczone karty", player.getNumberOfCards());
            if(index == 0)
                this.takeCountedCards();
            else{
                index--;
                this.cardsCountedPutCard(index);
            }
        }else{
            System.out.println("Nie masz jak się obronić, wciśnij Enter aby kontynuować");
            ConsoleReader reader = new ConsoleReader();
            reader.waitForEnter();
            this.takeCountedCards();
        }
    }

    private void cardsCountedPutCard(int index){
        Card card = player.getCertainCard(index);
        if(card.checkIfThrowableDraw(this.deckAndTable.getCurrentTopCard())){
            this.putCard(index);
        }else{
            System.out.println("Możesz wyrzucić tylko pasującą kartę typu DRAW2, lub kartę DRAW4");
            this.makeMoveDraw();
        }
    }

    private void takeCountedCards() {
        int numberOfCardsCounted = this.deckAndTable.getCardsCounted();
        this.deckAndTable.repairIfEmpty();
        Card firstCard = this.deckAndTable.remove();
        System.out.println("Pierwsza podebrana karta to: " + firstCard);
        if (firstCard.checkIfThrowable(this.deckAndTable.getCurrentTopCard()) && firstCard instanceof SuperCard && (((SuperCard) firstCard).getType() == SuperCardType.DRAW2 || ((SuperCard) firstCard).getType() == SuperCardType.DRAW4)){
            System.out.println("Mozesz wyrzucić tę kartę aby uchronić się od dobierania naliczonej liczby kart\nCzy chcesz to zrobić?");
            ConsoleReader reader = new ConsoleReader();
            if(reader.readYesOrNo())
                this.throwCard(firstCard);
            else
                this.giveCountedCardsToPlayer(firstCard, numberOfCardsCounted);
        }else{
            System.out.println("Nie możesz jej wyrzucić, wciśnij Enter, aby kontynuować");
            ConsoleReader reader = new ConsoleReader();
            reader.waitForEnter();
            this.giveCountedCardsToPlayer(firstCard, numberOfCardsCounted);
        }
    }

    private void giveCountedCardsToPlayer(Card firstCard, int numberOfCardsCouunted){
        player.addCard(firstCard);
        for(int i=1; i<numberOfCardsCouunted; i++) {
            this.deckAndTable.repairIfEmpty();
            player.addCard(this.deckAndTable.remove());
        }

        player.sortCards();
        this.deckAndTable.zeroCardsCounted();
        System.out.println("Liczba podebranych kart: " + numberOfCardsCouunted + "\nTak wyglądają Twoje karty:");
        player.writePlayersCards();
        System.out.println("Wciśnij Enter aby kontynuować");
        ConsoleReader reader = new ConsoleReader();
        reader.waitForEnter();
    }
}
