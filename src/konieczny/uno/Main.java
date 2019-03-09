package konieczny.uno;

import konieczny.uno.game.Game;

public class Main {

    public static void main(String[] args) {

        System.out.println("Witaj w grze UNO");

        while (true) {
            Game game = new Game();
            game.startGame();

            System.out.println("Czy chcesz zagrać jeszcze raz?");
            ConsoleReader reader = new ConsoleReader();
            if (!reader.readYesOrNo())
                break;
        }

        System.out.println("Dzięki za grę!");
    }

}
