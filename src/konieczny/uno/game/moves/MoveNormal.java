package konieczny.uno.game.moves;
import konieczny.uno.ConsoleReader;
import konieczny.uno.cards.Card;
import konieczny.uno.game.DeckAndTable;
import konieczny.uno.game.Player;

public class MoveNormal extends Move {

    public MoveNormal(Player player, DeckAndTable deckAndTable){
        super(player, deckAndTable);
    }

    public void makeMoveNormal(){
        if(player.checkIfThrowable(this.deckAndTable.getCurrentTopCard())){
            ConsoleReader reader = new ConsoleReader();
            int index = reader.readIndexOfCard("dobrać kartę", player.getNumberOfCards());
            if (index == 0)
                this.takeCard();
            else {
                index--;
                this.normalMovePutCard(index);
            }
        }else{
            System.out.println("Niestety żadna z Twoich kart nie nadaje się do wyrzucenia, musisz dobrać nową kartę, wciśnij Enter aby kontynuować");
            ConsoleReader reader = new ConsoleReader();
            reader.waitForEnter();
            this.takeCard();
        }
    }

    private void normalMovePutCard(int index){
        Card card = player.getCertainCard(index);
        if(card.checkIfThrowable(deckAndTable.getCurrentTopCard()))
            this.putCard(index);
        else {
            System.out.println("Nie możesz wyrzucić wybranej karty, spróbuj ponownie");
            this.makeMoveNormal();
        }
    }

    private void takeCard(){

        //dobierz kartę z talii
        deckAndTable.repairIfEmpty();
        Card card = deckAndTable.remove();
        System.out.println("Twoja nowa karta to:\n" + card);

        //sprawdz czy da się dorzucić nowo dobraną kartę, jeśli tak to zapytaj czy wyrzucić
        if (card.checkIfThrowable(deckAndTable.getCurrentTopCard())) {

            //Spytaj czy gracz chce wyrzucić nowo dobrana karte
            System.out.println("Czy chcesz wyrzucić nowo dobraną kartę?");
            ConsoleReader reader = new ConsoleReader();
            if(reader.readYesOrNo())
                this.throwCard(card);
            else {
                this.addToPlayersHand(card);
                System.out.println("Karta została dodana do Twich kart, zakończ ruch wciskając Enter");
                reader = new ConsoleReader();
                reader.waitForEnter();
            }
        } else {

            //jeśli nowo dobranej karty nie da się wyrzucić dodaj ją do ręki gracza
            this.addToPlayersHand(card);
            System.out.println("Nie możesz wyrzucić nowo dobranej karty, została ona dodana do Twojej ręki, zakończ ruch wciskając Enter");
            ConsoleReader reader = new ConsoleReader();
            reader.waitForEnter();

        }
    }

    private void addToPlayersHand(Card card){
        player.addCard(card);
        player.sortCards();
    }
}
