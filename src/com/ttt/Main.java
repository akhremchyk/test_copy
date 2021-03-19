package com.ttt;

import java.util.Scanner;

public class Main {

    private static final Field field = new Field();
    private static final Scanner scan = new Scanner(System.in);
    private static final Bot bot = new Bot();
    private static final Character playerSymbol1 = 'X';
    private static final Character playerSymbol2 = 'O';
    private static Character player = playerSymbol1;



    public static void main(String[] args) {

        field.printField();
        System.out.println("To fill a cell enter its number\n");
        bot.setSymbol(playerSymbol2);

        for (;;){
            gameBody();
            field.bla = true;
        }



    }

    private static void gameBody() {

//        TODO cls

        field.initialFill();
        field.printField();
        field.clearField();

        System.out.println("1. Player vs Computer");
        System.out.println("2. Player vs Player");

        player = playerSymbol1;

        for (; ; )
        {
            try
            {
                int sw; // switch
                if (scan.hasNextInt()) {
                    sw = scan.nextInt();
                } else {
                    scan.nextLine();
//                    to clear the input stream, ignore the rest of the symbols
//                     in the line with a mistake and show only one error message
                    throw new Exception("Enter a valid number");
                }

                if (sw == 1) {
                    bot.setState(true);
                    break;
                } else if (sw == 2) {
                    bot.setState(false);
                    break;
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

        field.printField();
        System.out.println("Player " + player + "'s turn");
        field.clearField();

        Character winner;
        for(;;)
        {
            turn();
            field.printField();
            winner = field.getWinner();
            if (winner != ' ')
            {
                break;
            }
            if (field.isFull())
            {
                break;
            }
            changePlayer();
        }
        end(winner);

    }

    private static void end (Character winner)
    {
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
            try{
                if (bot.isOn() && player == bot.getSymbol()){
                    Thread.sleep(300);
                    field.fillCell(bot.turn());
                }
                else {
                    field.fillCell(playerTurn());
                }
                return;
            }
            catch (Exception err) {
                if (bot.isOn() && player != bot.getSymbol())
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
        if (scan.hasNextInt()) {
            playerCell = scan.nextInt();
            return  playerCell;
        } else {
            scan.nextLine();
            return 0;
        }
    }

    public static char getCurrentPlayer ()
    {
        return player;
    }

    public static char getOtherPlayer ()
    {
        if (player == playerSymbol1)
           return playerSymbol2;
        else
            return playerSymbol1;
    }

    public static void changePlayer()
    {
        player = getOtherPlayer();
    }

    public static char getOtherSymbol(Character symbol){
        if (symbol == playerSymbol1)
            return playerSymbol2;
        else
            return playerSymbol1;
    }

    public static Field getField()
    {
        return field;
    }

}
