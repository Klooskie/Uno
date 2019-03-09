package konieczny.uno.game.moves;

import konieczny.uno.ConsoleReader;
import konieczny.uno.cards.*;
import konieczny.uno.cards.enums.SuperCardType;
import konieczny.uno.game.DeckAndTable;
import konieczny.uno.game.Player;

public class MoveSkips extends Move {

    public MoveSkips(Player player, DeckAndTable deckAndTable) {
        super(player, deckAndTable);
    }

    public void makeMoveSkips() {
        if (player.checkIfSkippable()) {
            System.out.println("\033[0;35mAby uniknąć opuszczania kolejek musisz wyrzucić kartę typu Stop\033[0m");
            ConsoleReader reader = new ConsoleReader();
            int index = reader.readIndexOfCard("opuścić kolejki (" + this.deckAndTable.getSkipsCounted() + ")", player.getNumberOfCards());
            if (index == 0) {
                this.skipTurn();
            } else {
                index--;
                this.skipsCountedPutCard(index);
            }
        } else {
            System.out.println("\033[0;35mNiesety nie masz jak zapobiec omijaniu kolejek\033[0m");
            this.skipTurn();
        }
    }

    private void skipsCountedPutCard(int index) {
        Card card = player.getCertainCard(index);
        if (card instanceof SuperCard && ((SuperCard) card).getType() == SuperCardType.SKIP) {
            this.putCard(index);
        } else {
            System.out.println("Nie możesz wyrzucić karty innej, niż karta typu Stop");
            this.makeMoveSkips();
        }
    }

    private void skipTurn() {
        System.out.println("Liczba kolejek do opuszczenia: " + this.deckAndTable.getSkipsCounted() + "\nWciśnij Enter, aby kontynuować");
        this.player.setSkips(this.deckAndTable.getSkipsCounted() - 1);
        deckAndTable.zeroSkipsCounted();
        ConsoleReader reader = new ConsoleReader();
        reader.waitForEnter();
    }
}
