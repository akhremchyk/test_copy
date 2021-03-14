package com.ttt;

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
        Character player = 'X';


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
            playerTurn();
        }

    }

    private static void playerTurn() {

        int userCell = 0;
        if (scan.hasNextInt()) {
            userCell = scan.nextInt();
        } else {
            // throw ; !!!!!!!!!!!!!!!!!!!!!!!!!
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
        field.printField();
    }
}
