package com.ttt;

import java.util.Random;

public class Main {

    private static final Field field = new Field();
    private static final Bot bot = new Bot();
    private static boolean isBotsSymbolRandom = false; // true = the bot's symbol is chosen randomly every game


    public static void main(String[] args) {

        if (args.length > 0 && args[0].equals("-c"))
        {
            field.printField();
            System.out.println("To fill a cell enter its number\n");
            bot.setSymbol(Field.getSecondSymbol());
            while (ConsoleUI.menu()) {
                try {
                    Thread.sleep(1000);
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }
            }
        }
        else if (args.length > 0)
        {
            System.out.println("Unknown argument " + args[0]);
            System.out.println("Use \"-c\" to activate console interface");
        }
        else {
            Gui gui = new Gui();
        }

    }

    public static void startGame()
    {
        if (Main.isIsBotsSymbolRandom())
        {
            Random rand = new Random();
            int symbolNumber = rand.nextInt(2)+1;
            if (symbolNumber == 1)
                bot.setSymbol(Field.getFirstSymbol());
            else
                bot.setSymbol(Field.getSecondSymbol());
        }

        field.setCurrentPlayer(Field.getFirstSymbol());
        field.clearField();

        Character winner;
        for(;;)
        {
            turn();
            field.printField();
            winner = field.checkWinner();
            if (winner != '0')
            {
                break;
            }

            field.changePlayer();
        }
        end(winner);

    }

    private static void turn() {

        for(;;)
        {
            try
            {
                if (bot.isOn() && field.getCurrentPlayer() == bot.getSymbol()){
                    Thread.sleep(300);
                    field.fillCell(bot.turn());
                }
                else {
                    field.fillCell(playerTurn());
                }
                return;
            }
            catch (Exception err) {
                if (!bot.isOn() || field.getCurrentPlayer() != bot.getSymbol())
//                in order not to show errors during the bot's turn
                {
                    System.out.println(err.getMessage());
                }
            }
        }
    }

    private static int playerTurn()
    {
        return 1;
    }

    private static void end (Character winner)
    {

        try{Thread.sleep(300);}
        catch (Exception err){ System.out.println(err.getMessage());}

        if (winner == ' ')
        {
            System.out.println("Draw!");
        }
        else
        {
            System.out.println("Player " + winner + " won!");
        }
    }

    public static Bot getBot(){return bot;}

    public static Field getField()
    {
        return field;
    }

    public static boolean isIsBotsSymbolRandom(){return isBotsSymbolRandom;}

    public static void setIsBotsSymbolRandom(boolean state){isBotsSymbolRandom = state;}

}
