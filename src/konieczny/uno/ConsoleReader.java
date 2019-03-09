package konieczny.uno;
import java.util.InputMismatchException;
import java.util.Scanner;
import konieczny.uno.cards.enums.Color;
import konieczny.uno.exceptions.BadNumberReadException;

public class ConsoleReader {

    Scanner scanner;

    public ConsoleReader(){
        this.scanner = new Scanner(System.in);
    }

    public void waitForEnter(){
        scanner.nextLine();
    }

    public boolean readYesOrNo(){
        String answer = "";
        while (!answer.equals("t") && !answer.equals("n")) {
            System.out.println("Wpisz \"t\" lub \"n\" i zatwierdź wciskając Enter");
            answer = scanner.nextLine();
        }
        return answer.equals("t");
    }

    public int readNumberOfPlayers(){
        int numberOfPlayers;
        try{
            numberOfPlayers = scanner.nextInt();
            if(numberOfPlayers < 2 || numberOfPlayers > 6)
                throw new BadNumberReadException();
        } catch (InputMismatchException | BadNumberReadException e){
            System.out.println("Wpisz liczbę od 2 do 6 i zatwierdź wciskając Enter");
            ConsoleReader reader = new ConsoleReader();
            return reader.readNumberOfPlayers();
        }
        return numberOfPlayers;
    }

    public String readPlayersName(){
        return scanner.nextLine();
    }

    public int readComparatorNo(){
        int comparatorNo ;
        try{
            comparatorNo = scanner.nextInt();
            if (comparatorNo < 1 || comparatorNo > 2)
                throw new BadNumberReadException();
        }catch(InputMismatchException | BadNumberReadException e){
            System.out.println("Wpisz \"1\" lub \"2\" i zatwierdź wciskając Enter");
            ConsoleReader reader = new ConsoleReader();
            return reader.readComparatorNo();
        }
        return comparatorNo;
    }

    public int readIndexOfCard(String s, int numberOfCards){
        System.out.println("Podaj numer karty od 1 do " + numberOfCards + ", którą chcesz wyrzucić, lub 0 jeśli chcesz " + s);
        int index;
        try{
            index = scanner.nextInt();
            if(index < 0 || index > numberOfCards)
                throw new BadNumberReadException();
        }catch(InputMismatchException | BadNumberReadException e){
            ConsoleReader reader = new ConsoleReader();
            return reader.readIndexOfCard(s, numberOfCards);
        }
        return index;
    }

    public Color readColor(){
        System.out.println("Wybierz numer od 1 do 4 odpowiadający pożądanemu kolorowi i zatwierdź klikając Enter");
        int colorID;

        try{
            colorID = scanner.nextInt();
            if(colorID < 1 || colorID > 4)
                throw new BadNumberReadException();
        }catch(InputMismatchException | BadNumberReadException e){
            ConsoleReader reader = new ConsoleReader();
            return reader.readColor();
        }

        Color color = Color.getColorByID(colorID);
        System.out.println("Wybrałeś kolor " + color);
        return color;
    }
}
