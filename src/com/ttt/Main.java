package com.ttt;

import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final Field field = new Field();
    private static final Scanner scan = new Scanner(System.in);
    private static boolean bot = false;
    private static Character player = 'X';

    public static void main(String[] args) {

        field.printField();
        System.out.println("To fill a cell enter its number\n");

        gameBody();

    }

    private static void gameBody() {

        // cls

        field.printField();

        System.out.println("1. Player vs Computer");
        System.out.println("2. Player vs Player");


        for (; ; )
        {
            try {
                int sw = 0;
                if (scan.hasNextInt()) {
                    sw = scan.nextInt(); //switch
                } else {
                    scan.nextLine();
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
//                    if (scan.hasNext())
//                    scan.next(); // to ignore the rest of the symbols in the line with a mistake
            }
        }

        field.printField();
        System.out.println("Player X's turn");
        field.clearField();

        for(;;)
        {
            turn();
            field.printField();
            if (player == 'X')
                player = 'O';
            else
                player = 'X';
        }

    }

    private static void turn(){

        int userCell;
        for(;;)
        {
            if (bot && player == 'O'){
                userCell = botTurn();
            }
            else {
                userCell = playerTurn();
            }
            try{
                if (userCell == 0 || userCell > 9)
                {
                    throw new Exception("Enter a valid cell number");
                }
                field.fillCell(userCell, player);
                return;
            }
            catch (Exception err) {
                if ((bot && player == 'X') || !bot)
                {
                    System.out.println(err.getMessage());
//                    if (scan.hasNext())
//                    scan.next(); // to ignore the rest of the symbols in the line with a mistake
                }
            }
        }
    }

    private static int botTurn() {

        /* Алгоритм бота основан на рандоме, ставит нули в случайные свободные
         * клетки, но если видит строку с двумя одинаковыми символами и
         * пробелом (т.е до победы какой-то из сторон остался один ход),
         * он ставит туда ноль, чтобы или выиграть самому или не дать выиграть
         * противнику, нули (т.е своя победа) в приоритете.
         */

        int botCell = 0;

   //     botCell = scanLines('O');
        if (botCell != 0) {
            //fill cell number botCell
        } else {
 //           botCell = scanLines('X');
        }

        if (botCell != 0) {
            //fill cell number botCell
        }
        else {
            Random rand = new Random();
            botCell = rand.nextInt(9) + 1;
            return botCell;
        }
        return 0;
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
}
