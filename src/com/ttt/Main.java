package com.ttt;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final Field field = new Field();
    private static final Scanner scan = new Scanner(System.in);
    private static boolean bot = false;
    private static final Character playerSymbol1 = 'X';
    private static final Character playerSymbol2 = 'O';
    private static Character botSymbol = playerSymbol2;
    private static Character player = playerSymbol1;


    public static void main(String[] args) {

        field.printField();
        System.out.println("To fill a cell enter its number\n");

        gameBody();

    }

    private static void gameBody() {

//        TODO cls

        field.printField();

        System.out.println("1. Player vs Computer");
        System.out.println("2. Player vs Player");


        for (; ; )
        {
            try {
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
                    bot = true;
                    break;
                } else if (sw == 2) {
                    bot = false;
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

        for(;;)
        {
            turn();
            field.printField();
            changePlayer();
        }

    }

    private static void turn() {

        for(;;)
        {
            try{
                if (bot && player == botSymbol){
                    field.fillCell(botTurn());
                }
                else {
                    field.fillCell(playerTurn());
                }
            }
            catch (Exception err) {
                if ((bot && player != botSymbol) || !bot) //FIXME
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

    private static Integer[] botTurn() throws Exception {

        // Bot scans for lines with two identical symbols and a clear cell and fills it.
        // Bot's symbol is in priority in order to win instantly.
        // If there are none such cells, it just fills a random cell.

        Integer[] botCell;

        botCell = scanForTwoAndClear(getCurrentPlayer());
        if (botCell != null) {
            return botCell;
        } else {
            botCell = scanForTwoAndClear(getOtherPlayer());
        }

        if (botCell != null) {
            return  botCell;
        }
        else {
            Random rand = new Random();
            return field.cellNumToCoord(rand.nextInt(9) + 1);
        }
    }

    private static Integer[] scanForTwoAndClear(char soughtSymbol)
    {
//      scans for a row with 2 identical symbols and a clear cell
        ArrayList<Character> checkedLine;

        for (int i = 0; i < 3; i++)
        {
            checkedLine = field.getRow(i);
            if (!checkedLine.contains(getOtherSymbol(soughtSymbol))
                    && checkedLine.contains(soughtSymbol))
            {
                int symbolCtr = 0; //counter to count how much sought symbols are there
                int clearCellCoord = 0; //coordinate of the clear cell
                for (int j = 0; j < 3; j++)
                {
                    if (checkedLine.get(j) == soughtSymbol)
                    {
                        symbolCtr++;
                    }
                    else {
                        clearCellCoord = j;
                    }
                }
                if (symbolCtr == 2)
                {
                    // if there are 2 sought symbols, there is only 1 free cell
                    return new Integer[]{i, clearCellCoord};
                }
            }
        }

        //scans for a column with 2 identical symbols and a clear cell
        for (int i = 0; i < 3; i++)
        {
            checkedLine = field.getColumn(i);
            if (!checkedLine.contains(getOtherSymbol(soughtSymbol))
                    && checkedLine.contains(soughtSymbol))
            {
                int symbolCtr = 0; //counter to count how much sought symbols are there
                int clearCellCoord = 0; //coordinate of the clear cell
                for (int j = 0; j < 3; j++)
                {
                    if (checkedLine.get(j) == soughtSymbol)
                    {
                        symbolCtr++;
                    }
                    else {
                        clearCellCoord = j;
                    }
                }
                if (symbolCtr == 2)
                {
                    // if there are 2 sought symbols, there is only 1 free cell
                    return new Integer[]{clearCellCoord, i};
                }
            }
        }

        //scans for a diagonal with 2 identical symbols and a clear cell
        for (int i = 0; i < 2; i++)
        {
            checkedLine = field.getDiagonal(i);
            if (!checkedLine.contains(getOtherSymbol(soughtSymbol))
                    && checkedLine.contains(soughtSymbol))
            {
                int symbolCtr = 0; //counter to count how much sought symbols are there
                int clearCellCoord = 0; //coordinate of the clear cell
                for (int j = 0; j < 3; j++)
                {
                    if (checkedLine.get(j) == soughtSymbol)
                    {
                        symbolCtr++;
                    }
                    else {
                        clearCellCoord = j;
                    }
                }
                if (symbolCtr == 2) {
                    // if there are 2 sought symbols, there is only 1 free cell
                    if (i == 0)
                        return new Integer[]{clearCellCoord, clearCellCoord};
                    else if (i == 1)
                        return new Integer[]{0 + clearCellCoord,2 - clearCellCoord};
                }
            }
        }

        return null;
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

}
