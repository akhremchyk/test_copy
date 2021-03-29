package com.ttt;

import java.util.Random;
import java.util.Scanner;

public class ConsoleUI
{
    private static final Scanner scan = new Scanner(System.in);
    private static final Bot bot = Main.getBot();
    private static final Field field = Main.getField();

    private static int scanInt() throws Exception
    {
        if (scan.hasNextInt()) {
            return scan.nextInt();
        } else {
            scan.nextLine();
//                    to clear the input stream, ignore the rest of the symbols
//                     in the line with a mistake and show only one error message
            throw new Exception("Enter a valid number");
        }
    }

    public static boolean menu() {

//        TODO cls

        field.initialFill();
        field.printField();
        field.setCurrentPlayer(Field.getFirstSymbol());

        System.out.println("1. Play");
        System.out.println("2. Bot's settings");
        System.out.println("3. Quit");

        for (; ; )
        {
            try
            {
                int sw = scanInt(); // switch

                if (sw == 1) {
                    game();
                    break;
                }
                else if (sw == 2) {
                    botSettings();
                    field.printField();
                    System.out.println("1. Play");
                    System.out.println("2. Bot's settings");
                    System.out.println("3. Quit");
                }
                else if (sw == 3)
                {
                    return false;
                }
                else
                {
                    throw new Exception("Enter a valid number");
                }
            }
            catch (Exception err)
            {
                System.out.println(err.getMessage());
//                     FIXME if (scan.hasNext())
//                      scan.next();
            }
        }
        return true;
    }

    private static void game()
    {

        if (Main.isBotsSymbolRandom())
        {
            Random rand = new Random();
            int symbolNumber = rand.nextInt(2)+1;
            if (symbolNumber == 1)
                bot.setSymbol(Field.getFirstSymbol());
            else
                bot.setSymbol(Field.getSecondSymbol());
        }

        System.out.println("1. Player vs Computer");
        System.out.println("2. Player vs Player");

        for(;;)
        {
            try {
                int sw = scanInt(); // switch
                if (sw == 1) {
                    bot.setState(true);
                    break;
                } else if (sw == 2) {
                    bot.setState(false);
                    break;
                }
                else
                    throw new Exception("Enter a valid number");
            } catch (Exception err) {
                System.out.println(err.getMessage());
//                     FIXME if (scan.hasNext())
//                      scan.next();
            }
        }

        field.initialFill();
        field.printField();
        System.out.println("Player " + field.getCurrentPlayer() + "'s turn\n");
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

    private static void botSettings() {

        System.out.println("1. Bot's difficulty");
        System.out.println("2. Bot's symbol");

        for(;;)
        {
            try {
                int sw = scanInt(); // switch
                if (sw == 1) {
                    chooseBotsDifficulty();
                    break;
                }
                else if (sw == 2) {
                    chooseBotsSymbol();
                    break;
                }
                else
                {
                    throw new Exception("Enter a valid number");
                }
            } catch (Exception err) {
                System.out.println(err.getMessage());
//                     FIXME if (scan.hasNext())
//                      scan.next();
            }
        }

    }

    private static void chooseBotsDifficulty() {

        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
        System.out.println("4. Cheater");
        for (; ; )
        {
            try {
                int difficulty = scanInt();
                bot.setDifficulty(difficulty);
                return;
            } catch (Exception err) {
                System.out.println(err.getMessage());
                //                     FIXME if (scan.hasNext())
                //                      scan.next();
            }
        }
    }

    private static void chooseBotsSymbol()
    {
        System.out.println("1. Always " + Field.getSecondSymbol());
        System.out.println("2. Always " + Field.getFirstSymbol());
        System.out.println("3. Random every time");
        for (; ; )
        {
            try {
                int value = scanInt(); // switch
                if (value < 1 || value > 3)
                    throw new Exception("Enter a valid number");
                else if (value == 3)
                    Main.setIsBotsSymbolRandom(true);

                if (value == 1)
                    bot.setSymbol(Field.getSecondSymbol());
                else
                    bot.setSymbol(Field.getFirstSymbol());

                return;

            } catch (Exception err) {
                System.out.println(err.getMessage());
                //                     FIXME if (scan.hasNext())
                //                      scan.next();
            }
        }
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
//                   FIXME if (scan.hasNext())
//                    scan.next();
                }
            }
        }
    }

    private static int playerTurn(){
        int playerCell;
        if (scan.hasNextInt())
        {
            playerCell = scan.nextInt();
            return  playerCell;
        } else
        {
            scan.nextLine();
            return 0;
        }
    }

}
