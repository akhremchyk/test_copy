package com.ttt;

import java.util.HashMap;

public class Field {

    private char[][] cell = new char[3][3];
    private HashMap<Integer, Integer[]> cellNumbers = new HashMap<Integer, Integer[]>();

    public Field() {
        initialFill();

//        Initializing cellNumber HashMap
        Integer number = 1;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                Integer[] coordinates = {i, j};
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

    public void initialFill() {
//         initial indication of cells' numbers
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
                    Integer[] cellCoord = cellNumbers.get(userCell);
                    if (cell[cellCoord[0]][cellCoord[1]] == ' ')
                    {
                        cell[cellCoord[0]][cellCoord[1]] = player;
                        return;
                    } else {
                        throw new Exception("The cell is already occupied");
                    }
    }

    public char[] getLine(int lineNum)
    {
        return cell[lineNum];
    }

}


