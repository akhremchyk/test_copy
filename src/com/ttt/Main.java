package com.ttt;

import com.ttt.GUI.Gui;

public class Main {

    private static final Field field = new Field();
    private static final Bot bot = new Bot();
    private static boolean isBotsSymbolRandom = false; // true = the bot's symbol is chosen randomly every game
    private static Gui gui;


    public static void main(String[] args) {

        // By default the game opens with GUI
        // But adding "-c" (console) flag activates console UI
        if (args.length > 0 && args[0].equals("-c"))
        {
            ConsoleUI.clearScreen();
            field.printField();
            System.out.println("To fill a cell enter its number\n");
            ConsoleUI.pause();
            while (true)
            {
                if (!ConsoleUI.menu())
                {
                    break;
                }
            }
        }
        else if (args.length > 0)
        {
            System.out.println("\nUnknown argument \"" + args[0] + "\"");
            System.out.println("Use \"-c\" to activate console interface\n");
        }
        else {
            gui = new Gui();
        }



    }

    public static Gui getGui() { return gui; }

    public static Bot getBot(){ return bot; }

    public static Field getField() { return field; }

    public static boolean isBotsSymbolRandom(){ return isBotsSymbolRandom; }

    public static void setIsBotsSymbolRandom(boolean state){ isBotsSymbolRandom = state; }

}
