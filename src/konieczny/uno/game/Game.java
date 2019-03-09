package konieczny.uno.game;

import konieczny.uno.cards.*;
import konieczny.uno.cards.enums.*;
import konieczny.uno.game.moves.*;
import konieczny.uno.ConsoleReader;

public class Game {

    private DeckAndTable deckAndTable;
    private int numberOfPlayers;
    private Player[] players;
    private int currentPlayer;

    public Game() {
        this.deckAndTable = new DeckAndTable();
        this.currentPlayer = 0;
    }

    //przygotowanie gry
    public void startGame() {
        this.prepareDeck();
        this.preparePlayers();
        this.showPlayers();
        this.dealCards();
        this.prepareTable();
        this.theGame();
    }

    private void prepareDeck() {

        //Dodajemy odpowiednie karty do talii
        //Po jednej karcie 0 kazdego koloru
        deckAndTable.add(new NormalCard(Color.RED, 0));
        deckAndTable.add(new NormalCard(Color.GREEN, 0));
        deckAndTable.add(new NormalCard(Color.BLUE, 0));
        deckAndTable.add(new NormalCard(Color.YELLOW, 0));

        //Po 2 karty 1-9 każdego koloru
        for (int j = 1; j <= 9; j++) {
            for (int i = 0; i < 2; i++) {
                deckAndTable.add(new NormalCard(Color.RED, j));
                deckAndTable.add(new NormalCard(Color.GREEN, j));
                deckAndTable.add(new NormalCard(Color.BLUE, j));
                deckAndTable.add(new NormalCard(Color.YELLOW, j));
            }
        }

        //Po 2 karty SKIP, REVERSE, DRAW2 każdego koloru
        for (int i = 0; i < 2; i++) {
            deckAndTable.add(new SuperCard(Color.RED, SuperCardType.SKIP));
            deckAndTable.add(new SuperCard(Color.GREEN, SuperCardType.SKIP));
            deckAndTable.add(new SuperCard(Color.BLUE, SuperCardType.SKIP));
            deckAndTable.add(new SuperCard(Color.YELLOW, SuperCardType.SKIP));

            deckAndTable.add(new SuperCard(Color.RED, SuperCardType.REVERSE));
            deckAndTable.add(new SuperCard(Color.GREEN, SuperCardType.REVERSE));
            deckAndTable.add(new SuperCard(Color.BLUE, SuperCardType.REVERSE));
            deckAndTable.add(new SuperCard(Color.YELLOW, SuperCardType.REVERSE));

            deckAndTable.add(new SuperCard(Color.RED, SuperCardType.DRAW2));
            deckAndTable.add(new SuperCard(Color.GREEN, SuperCardType.DRAW2));
            deckAndTable.add(new SuperCard(Color.BLUE, SuperCardType.DRAW2));
            deckAndTable.add(new SuperCard(Color.YELLOW, SuperCardType.DRAW2));
        }

        //Po 4 DRAW4, COLORCHANGE
        for (int i = 0; i < 4; i++) {
            deckAndTable.add(new SuperCard(Color.WILD, SuperCardType.DRAW4));
            deckAndTable.add(new SuperCard(Color.WILD, SuperCardType.COLORCHANGE));
        }

        //Przetasuj talię
        deckAndTable.shuffleDeck();
    }

    private void preparePlayers() {

        //Wprowadzanie liczby graczy
        System.out.println("Podaj liczbe grzaczy (max 6): ");
        ConsoleReader reader = new ConsoleReader();
        numberOfPlayers = reader.readNumberOfPlayers();

        //Tworzenie tablicy graczy
        players = new Player[numberOfPlayers];

        //Wprowadzanie nazw graczy
        for (int i = 0; i < numberOfPlayers; i++) {

            System.out.println("Podaj nazwę gracza nr " + (i + 1) + " i zatwierdź wciskając Enter, lub wciśnij Enter, aby ustawić nazwę doyślną");
            reader = new ConsoleReader();
            players[i] = new Player(i, reader.readPlayersName());
            System.out.println("Wybierz metodę sortowania kart dla gracza nr " + (i + 1) + "\nMożliwe metody sortowania do wyboru to:\n\033[1;30m1. \033[0mSortowanie kart według koloru\n\033[1;30m2. \033[0mSortowanie kart według wartości");
            reader = new ConsoleReader();
            players[i].setComparator(reader.readComparatorNo());

        }
    }

    private void showPlayers() {

        //Wypisywanie nazw graczy
        System.out.println("\nOto nasi gracze:");
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("\033[1;30mGracz " + (i + 1) + ": \033[0m" + players[i].getName());
        }
        System.out.println("Zaczynajmy więc!\nWciśnij Enter aby kontynuować");
        ConsoleReader reader = new ConsoleReader();
        reader.waitForEnter();
    }

    private void dealCards() {

        //rozdawanie kart graczom
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < numberOfPlayers; j++) {
                players[j].addCard(deckAndTable.remove());
            }
        }

        //sortowanie rąk graczy
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i].sortCards();
        }
    }

    private void prepareTable() {

        //ominiecie kart specjalnych, zeby nie zaczynać od nich gry
        while (deckAndTable.element() instanceof SuperCard) {
            deckAndTable.usedCardAdd(deckAndTable.remove());
        }

        //ustawienie karty nromalnej na kartę znajdującą się na górze stosu
        deckAndTable.topToCurrent();
    }

    private void theGame() {

        while (true) {
            //Wypisanie kreski oddzielajacej poszczególne ruchy
            System.out.println("\u001B[36m_____________________________________________________________________________________________\033[0m\n");

            //Wypisanie wiadomości o akualnym graczu i jego kart
            System.out.println("Kolej gracza nr " + (currentPlayer + 1) + " (" + players[currentPlayer] + ")");

            //Sprawdzenie czy dany gracz nie stoi kolejki
            if (this.checkIfSkippingTurn()) {
                continue;
            }

            //Wypisanie aktualnej karty z gory stosu i kart gracza
            System.out.println("Aktualna karta na górze stosu to:\n" + deckAndTable.getCurrentTopCard() + "\nOto Twoje karty:");
            players[currentPlayer].writePlayersCards();

            //Dokonywanie ruchu, 3 warianty w zaleznosci od tego czy naliczone sa karty do podebrania lub kolejki do ominiecia
            if (deckAndTable.getSkipsCounted() != 0) {
                MoveSkips move = new MoveSkips(players[currentPlayer], deckAndTable);
                move.makeMoveSkips();
            } else if (deckAndTable.getCardsCounted() != 0) {
                MoveDraw move = new MoveDraw(players[currentPlayer], deckAndTable);
                move.makeMoveDraw();
            } else {
                MoveNormal move = new MoveNormal(players[currentPlayer], deckAndTable);
                move.makeMoveNormal();
            }


            //30 enterow
            for (int i = 0; i < 30; i++) System.out.println();

            //Wypisywanie UNO jeśli gracz ma tylko jedną kartę
            if (players[currentPlayer].getNumberOfCards() == 1) {
                System.out.println("\033[1;30mUNO! \033[1;31mUNO! \033[1;32mUNO! \033[1;34mUNO! \033[1;33mUNO! \033[1;31mUNO! \033[1;32mUNO! \033[1;34mUNO! \033[1;33mUNO! \033[1;31mUNO! \033[1;32mUNO! \033[1;34mUNO! \033[1;33mUNO! \033[1;31mUNO! \033[1;32mUNO! \033[1;34mUNO! \033[1;33mUNO! \033[1;30mUNO!\n\033[0m");
            }

            //Kończenie gry jeśli gracz nie ma już kart
            if (players[currentPlayer].getNumberOfCards() == 0) {
                System.out.println("\n\033[1;30mKoniec\nZwyciężył gracz nr " + (currentPlayer + 1) + "\nGratulacje " + players[currentPlayer] + "\n\033[0m");
                break;
            }

            //Ustalenie następnego gracza
            this.setNextPlayer();
        }
    }

    private void setNextPlayer() {
        if (deckAndTable.getDirection())
            this.currentPlayer = (this.currentPlayer + 1) % this.numberOfPlayers;
        else
            this.currentPlayer = (this.numberOfPlayers + this.currentPlayer - 1) % this.numberOfPlayers;
    }

    private boolean checkIfSkippingTurn() {
        if (players[currentPlayer].getSkips() != 0) {
            players[currentPlayer].decreaseSkips();
            System.out.println("Gracz " + (currentPlayer + 1) + " opuszcza kolejkę\nPozostała liczba kolejek, które będziesz musiał opuścić: " + players[currentPlayer].getSkips() + "\nWciśnij Enter aby kontynuować");
            ConsoleReader reader = new ConsoleReader();
            reader.waitForEnter();
            this.setNextPlayer();
            return true;
        }
        return false;
    }

}
