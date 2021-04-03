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
        // Scanning the whole line
        String line = scan.nextLine();

         if (line.length() == 0)
        {
            throw new Exception("");
        }
         // Checking it's first symbol, it should be a digit
        else if (line.charAt(0) < '0' || line.charAt(0) > '9' || line.length() > 1) {
            throw new Exception("Enter a valid number");
        }
        else {
            // ASCII to int (digit's code - 48 == digit itself)
            return line.charAt(0) - 48;
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
            }
        }
        return true;
    }

    private static void game()
    {

        // If it was set to give bot random symbol every new game
        // The symbol is being chosen randomly every new game (thanks cap obvious)
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

            // The game ends if there is a winner or there are no free cells
            // If bot's difficulty is "Cheater" it doesn't accept draw and places it's symbol anyway
            if (winner != '0' && ((bot.getDifficulty() != 4) || (!bot.isOn())))
            {
                break;
            }
            // And if the bot wins, the game can end
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
                // In order to not to show errors during the bot's turn
                {
                    System.out.println(err.getMessage());
                }
            }
        }
    }

    private static int playerTurn(){
        String line = scan.nextLine();
        if (line.length() == 0)
        {
            return 0;
        }
        else if (line.charAt(0) < '0' || line.charAt(0) > '9' || line.length() > 1) {
            return 0;
        } else {
            return line.charAt(0) - 48;
        }
    }

    public static void clearScreen()
    {
        // Executes console command to clear console's screen
        // If the program runs in Windows it uses "cls"
        final String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Else it types this ansi escape sequences
        // \033[H moves the cursor to the top left corner of the screen
        // And \033[J clears the part of the screen from the cursor to the end of the screen
        // Just in case flush() flushes this output stream and forces any buffered output bytes to be written out
        else
        {
            System.out.print("\033[H\033[J");
            System.out.flush();
        }
    }

    public static void pause()
    {
        System.out.println("Press enter to continue");
        scan.nextLine();
    }

}
