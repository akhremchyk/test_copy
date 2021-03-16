package com.ttt;

import java.util.Arrays;

public class Field {

    private char[][] cell = new char[3][3];

    public Field() {
        this.initialFill();
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

    public void initialFill() {

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
