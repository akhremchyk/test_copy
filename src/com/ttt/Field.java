package com.ttt;

import java.util.ArrayList;
import java.util.HashMap;

public class Field
{

    private final Character[][] cell = new Character[3][3];
    private final HashMap<Integer, Integer[]> cellNumbers = new HashMap<>();
    private static final Character playerSymbol1 = 'X';
    private static final Character playerSymbol2 = 'O';
    private Character currentPlayer = playerSymbol1;

    // Coordinates of cells in winning combination.
    // Needed to highlight them in GUI.
    private Integer[][] winningCombination = new Integer[3][2];


    public Field() {
        initialFill();

        // Initializing cellNumber HashMap
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

    // Initial indication of cells' numbers
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
            cell[cellCoords[0]][cellCoords[1]] = getCurrentPlayer();
        } else {
            throw new Exception("The cell is already occupied");
        }
    }

    public void clearCell(Integer[] cellCoords)
    {
        cell[cellCoords[0]][cellCoords[1]] = ' ';
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
        ArrayList<Character> line = new ArrayList<>(3);
        for (int i = 0; i < 3; i++)
        {
            if (diagonalNum == 0)
                line.add(i, cell[i][i]);
            else if (diagonalNum == 1)
                line.add(i, cell[i][2-i]);
        }
        return line;
    }

    // Scans if there are any free cells
    public boolean isFull()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (cell[i][j] == ' ')
                    return false;
            }
        }
        return true;
    }

    // Scans for winning combinations
    public Character checkWinner()
    {
        for (int i = 0; i < 3; i++)
        {
            ArrayList<Character> row = getRow(i);
            if ((row.get(0) == row.get(1)) && (row.get(1) == row.get(2))
                    && row.get(0) != ' ')
            {
                setWinningCombination('r', i);
                return row.get(0);
            }
        }
        for (int i = 0; i < 3; i++)
        {
            ArrayList<Character> column = getColumn(i);
            if ((column.get(0) == column.get(1)) && (column.get(1) == column.get(2))
                    && column.get(0) != ' ')
            {
                setWinningCombination('c', i);
                return column.get(0);
            }
        }
        for (int i = 0; i < 2; i++)
        {
            ArrayList<Character> diagonal = getDiagonal(i);
            if ((diagonal.get(0) == diagonal.get(1)) && (diagonal.get(1) == diagonal.get(2))
                    && diagonal.get(0) != ' ')
            {
                setWinningCombination('d', i);
                return diagonal.get(0);
            }
        }
        if (isFull())
            return ' ';
        else
            return '0';
    }

    // Sets winningCombination that is being used to highlight it in GUI
    public void setWinningCombination(char line, int number)
    {
        // char line - indicator for whether row(r), column(c) or diagonal(d) won
        // int number - its number
        if (line == 'r')
        {
            for(int i = 0; i < 3; i++)
            {
                winningCombination[i][0] = number;
                winningCombination[i][1] = i;
            }
        }
        else if (line == 'c')
        {
            for(int i = 0; i < 3; i++)
            {
                winningCombination[i][0] = i;
                winningCombination[i][1] = number;
            }
        }
        else if (line == 'd')
        {
            for(int i = 0; i < 3; i++)
            {
                if (number == 0)
                {
                    winningCombination[i][0] = i;
                    winningCombination[i][1] = i;
                }
                else
                {
                    winningCombination[i][0] = i;
                    winningCombination[i][1] = 2 - i;
                }
            }
        }
        else
        {
            winningCombination = null;
        }
    }

    public Integer[][] getWinningCombination()
    {
        return winningCombination;
    }

    public void initialFillGui()
    {
        cell[0][0] = 'T';
        cell[0][1] = 'I';
        cell[0][2] = 'C';
        cell[1][0] = 'T';
        cell[1][1] = 'A';
        cell[1][2] = 'C';
        cell[2][0] = 'T';
        cell[2][1] = 'O';
        cell[2][2] = 'E';
    }

    public void setCurrentPlayer(Character input)
    {
        currentPlayer = input;
    }

    public Character getCurrentPlayer()
    {
        return currentPlayer;
    }

    public Character getOtherPlayer()
    {
        if (currentPlayer == playerSymbol1)
            return playerSymbol2;
        else
            return playerSymbol1;
    }

    public void changePlayer()
    {
        currentPlayer = getOtherPlayer();
    }

    public static Character getOtherSymbol(Character symbol){
        if (symbol == playerSymbol1)
            return playerSymbol2;
        else
            return playerSymbol1;
    }

    public static Character getFirstSymbol()
    {
        return playerSymbol1;
    }

    public static Character getSecondSymbol()
    {
        return playerSymbol2;
    }

    public Character[][] getCellArray()
    {
        return cell;
    }

}


