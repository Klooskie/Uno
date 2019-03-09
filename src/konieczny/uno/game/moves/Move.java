package konieczny.uno.game.moves;
import konieczny.uno.ConsoleReader;
import konieczny.uno.cards.*;
import konieczny.uno.cards.enums.*;
import konieczny.uno.game.DeckAndTable;
import konieczny.uno.game.Player;

abstract class Move {
    Player player;
    DeckAndTable deckAndTable;

    Move(Player player, DeckAndTable deckAndTable) {
        this.player = player;
        this.deckAndTable = deckAndTable;
    }

    //Wyrzucenie tylko jednej karty, którą właśnie się dobrało
    void throwCard(Card card){
        deckAndTable.setCurrentTopCard(card);
        deckAndTable.usedCardAdd(card);
        System.out.println("Karta " + card + " została dołożona na stół");
        this.dealWithSuperCard(card);
        System.out.println("Zakończyłeś swój ruch, wciśnij Enter, aby kontynuować");
        ConsoleReader reader = new ConsoleReader();
        reader.waitForEnter();
    }

    //Wyrzucenie wybarnej przez siebie karty, możliwość wyrzucenia kilku
    void putCard(int index){
        Card card = player.getCertainCard(index);
        deckAndTable.setCurrentTopCard(card);
        deckAndTable.usedCardAdd(card);
        player.removeCertainCard(index);
        System.out.println("Karta " + card + " została dołożona na stół");
        this.dealWithSuperCard(card);
        if(player.checkIfAddable(this.deckAndTable.getCurrentTopCard())) {
            this.putAnotherCard();
        }else{
            System.out.println("Nie możesz wyrzucić więcej kart, wciśnij Enter aby zakończyć ruch");
            ConsoleReader reader = new ConsoleReader();
            reader.waitForEnter();
        }
    }

    //dorzucanie kolejnej karty
    private void putAnotherCard(){
        System.out.println("Możesz wyrzucić kolejną kartę tego samego rodzaju\nTwoje karty to:");
        player.writePlayersCards();
        ConsoleReader reader = new ConsoleReader();
        int index = reader.readIndexOfCard("zakończyć ruch", player.getNumberOfCards());

        if(index == 0){
            System.out.println("Zakończyłeś swój ruch, wcisnij Enter, aby kontynuować");
            reader = new ConsoleReader();
            reader.waitForEnter();
        }else {
            index--;
            if (player.getCertainCard(index).checkIfAddable(deckAndTable.getCurrentTopCard())) {
                this.putCard(index);
            } else {
                System.out.println("Nie możesz wyrzucić wybranej karty, wyrzuciłeś już kartę: " + deckAndTable.getCurrentTopCard());
                this.putAnotherCard();
            }
        }
    }

    private void dealWithSuperCard(Card card){
        if(card instanceof SuperCard){
            SuperCardType type = ((SuperCard)card).getType();
            if(type == SuperCardType.SKIP){
                System.out.println("\033[0;35mKolejka do opuszczenia została naliczona\033[0m");
                deckAndTable.increaseSkipsCounted();
            } else if(type == SuperCardType.REVERSE){
                System.out.println("\033[0;35mKierunek został odwrócony\033[0m");
                deckAndTable.negateDirection();
            } else if(type == SuperCardType.DRAW2){
                System.out.println("\033[0;35mNaliczona liczba kart do podebrania została zaktualizowana\033[0m");
                deckAndTable.increaseCardsCounted(2);
            } else if(type == SuperCardType.DRAW4){
                System.out.println("\033[0;35mNaliczona liczba kart do podebrania została zaktualizowana\033[0m");
                deckAndTable.increaseCardsCounted(4);
                Color.writeColors();
                ConsoleReader reader = new ConsoleReader();
                deckAndTable.changeColor(reader.readColor());
            } else { //type == SuperCardType.COLORCHANGE
                Color.writeColors();
                ConsoleReader reader = new ConsoleReader();
                deckAndTable.changeColor(reader.readColor());
            }
        }
    }
}
