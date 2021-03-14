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


        for (; ; ) {
            int sw = 0;
            if (scan.hasNextInt()) {
                sw = scan.nextInt(); //switch
            } else {
                // throw 1; !!!!!!!!!!!!!!!!!!!!!!!!!
            }

            if (sw == 1) {
                bot = true;
                break;
            } else if (sw == 2) {
                bot = false;
                break;
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
        int userCell = 0;

        if (bot && player == 'O'){
            userCell = botTurn();
        }
        else {
            userCell = playerTurn();
        }

        int checkCell = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++){
                checkCell++;
                if (checkCell == userCell)
                {
                    if (field.cell[i][j] == ' ') {
                        field.cell[i][j] = player;
                    }
                    else
                    {
                        System.out.println("The cell is already occupied"); // trow; !!!!
                    }
                    break;
                }
            }
            if (checkCell == userCell)
            {
                break;
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

    private static int playerTurn() {

        int playerCell = 0;
        if (scan.hasNextInt()) {
            playerCell = scan.nextInt();
            return  playerCell;
        } else {
            // throw ; !!!!!!!!!!!!!!!!!!!!!!!!!
        }
        return 0;

    }
}
