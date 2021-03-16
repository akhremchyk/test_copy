package com.ttt;

import java.util.HashMap;

public class Field {

    private char[][] cell = new char[3][3];
    private HashMap<Integer, Integer[]> cellNumbers = new HashMap<Integer, Integer[]>();

    public Field() {
        this.initialFill();
        int number = 1;
        Integer[] coordinates = new Integer[2];
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                coordinates[0] = i;
                coordinates[1] = j;
                cellNumbers.put(number, coordinates);
                number++;
            }
        }
    }


    public void printField()
    {
        System.out.println(
                                "\t " + cell[0][0] + " | " + cell[0][1] + " | " + cell[0][2] + " \n" +
                                "\t---|---|---\n" +
                                "\t " + cell[1][0] + " | " + cell[1][1] + " | " + cell[1][2] + " \n" +
                                "\t---|---|---\n" +
                                "\t " + cell[2][0] + " | " + cell[2][1] + " | " + cell[2][2] + " \n");
    }

    public void initialFill() { // initial indication of cells' numbers
        char cell_num = '1';
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++) {
                cell[i][j] = cell_num;
                cell_num ++;
            }
        }
    }

    public void clearField()
    {
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                cell[i][j] = ' ';
            }
        }
    }

    public void fillCell(int userCell, char player) throws Exception
    {
        int checkCell = 0;
        for (int i = 0; i < 3; i++)
            //goes through every cell increasing counter(checkCell) until
            //counter == cell number chosen by user
            //fills the cell on which the cycle stopped
        {
            for (int j = 0; j < 3; j++)
            {
                checkCell++;
                if (checkCell == userCell)
                {
                    if (cell[i][j] == ' ')
                    {
                        cell[i][j] = player;
                        return;
                    } else {
                        throw new Exception("The cell is already occupied");
                    }
                }
            }
        }
    }
}
