package com.ttt;

import java.io.Console;
import java.util.Scanner;

public class Main {

    private static Field field = new Field();
    private static Scanner scan = new Scanner(System.in);
    private static boolean bot = false;

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

//        for (;;)
//        {
//             try
//            {
//                //Если бот включен и его ход
//                if (bot && player == 'O')
//                    botTurn(player);
//
//                else
//                    turn(player);
//
//                if (check_end() != 'c')
//                    break;
//
//                if ((bot) || (bot && player == 'X'))
//                    cout << "Player " << player << "'s turn" << endl;
//            }
//            catch(int err)
//            {
//                //Не выводит ошибки при ходе бота
//                if (err == 1 && ( (!bot) ||
//                        (bot && player == 'X') ) )
//                {
//                    cout << "The cell is already occupied" << endl;
//                }
//                if (err == 2 && ( (!bot) ||
//                        (bot && player == 'X') ) )
//                {
//                    cout << "Enter a valid cell number" << endl;
//                }
//            }
//        }
    }

}
