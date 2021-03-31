package com.ttt;

import java.io.IOException;
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

        clearScreen();
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
                    clearScreen();
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

        clearScreen();
        field.printField();
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

        clearScreen();
        field.initialFill();
        field.printField();
        System.out.println("Player " + field.getCurrentPlayer() + "'s turn\n");
        field.clearField();

        Character winner;
        for(;;)
        {
            turn();
            clearScreen();
            field.printField();
            field.changePlayer();
            winner = field.checkWinner();

            if (winner != '0' && ((bot.getDifficulty() != 4) || (!bot.isOn())))
            {
                break;
            }
            else if (winner == bot.getSymbol() && bot.getDifficulty() == 4 && bot.isOn())
            {
                break;
            }
            else
            {
                System.out.println("Player " + field.getCurrentPlayer() + "'s turn\n");
            }

        }
        end(winner);
    }

    private static void botSettings() {

        clearScreen();
        field.printField();
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

        clearScreen();
        field.printField();
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
        clearScreen();
        field.printField();
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
            System.out.println("Player " + winner + " won!\n");
        }

        // In the end of the game something is left in input stream
        // So it should be cleaned at first and then it can wait for user's input
        // Or pause() just doesn't work
        // *Костыль*
        scan.nextLine();
        pause();

    }

    private static void turn() {

        for(;;)
        {
            try
            {
                if (bot.isOn() && field.getCurrentPlayer() == bot.getSymbol()){
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

    public static void clearScreen()
    {
        final String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    public static void pause()
    {
        System.out.println("Press enter to continue");
        scan.nextLine();
    }

}
