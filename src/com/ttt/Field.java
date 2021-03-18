package com.ttt;

import java.util.ArrayList;
import java.util.HashMap;

public class Field {

    private final char[][] cell = new char[3][3];
    private final HashMap<Integer, Integer[]> cellNumbers = new HashMap<>();

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

    public void fillCell(int userCell) throws Exception
    {
        if (userCell == 0 || userCell > 9)
        {
            throw new Exception("Enter a valid cell number");
        }
        fillCell(cellNumbers.get(userCell));
    }

    public void fillCell(Integer[] cellCoords) throws Exception
    {

        if (cell[cellCoords[0]][cellCoords[1]] == ' ')
        {
            cell[cellCoords[0]][cellCoords[1]] = Main.getCurrentPlayer();
        } else {
            throw new Exception("The cell is already occupied");
        }
    }

    public ArrayList<Character> getRow(int rowNum)
    {
        ArrayList<Character> line = new ArrayList<>(3);
        for (int i = 0; i < 3; i++)
        {
            line.add(i, cell[rowNum][i]);
        }
        return line;
    }

    public ArrayList<Character> getColumn(int columnNum)
    {
        ArrayList<Character> line = new ArrayList<>(3);
        for (int i = 0; i < 3; i++)
        {
            line.add(i, cell[i][columnNum]);
        }
        return line;
    }

    public Integer[] cellNumToCoord(int cellNum)
    {
        return cellNumbers.get(cellNum);
    }

    public ArrayList<Character> getDiagonal(int diagonalNum)
    {
        //
        ArrayList<Character> line = new ArrayList<>(3);
        for (int i = 0; i < 3; i++)
        {
            if (diagonalNum == 0)
                line.add(i, cell[i][i]);
            else if (diagonalNum == 1)
                line.add(i, cell[0+i][2-i]);
        }
        return line;
    }

}


