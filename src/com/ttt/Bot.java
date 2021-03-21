package com.ttt;

import java.util.ArrayList;
import java.util.Random;

public class Bot {

    private Character symbol;
    private boolean isOn; // indicates whether the bot is working or not
    private final Field field = Main.getField();
    private int difficulty = 3;



    public Integer[] turn()
    {
        if (difficulty == 1)
            return easyDifficulty();
        else if (difficulty == 3) {
            return hardDifficulty();
        }
        else
            return mediumDifficulty();

    }

    private Integer[] easyDifficulty()
    {
        Random rand = new Random();
        return field.cellNumToCoord(rand.nextInt(9) + 1);
    }

    private Integer[] mediumDifficulty()
    {
        // Bot scans for lines with two identical symbols and a clear cell and fills it.
        // Bot's symbol is in priority in order to win instantly.
        // If there are none such cells, it just fills a random cell.

        Integer[] botCell;

        botCell = scanForTwoAndClear(field.getCurrentPlayer());
        if (botCell != null) {
            return botCell;
        } else {
            botCell = scanForTwoAndClear(field.getOtherPlayer());
        }

        if (botCell != null) {
            return  botCell;
        }
        else {
            Random rand = new Random();
            return field.cellNumToCoord(rand.nextInt(9) + 1);
        }
    }

    private Integer[] hardDifficulty()
    {
        Integer[] bestMove = {0, 0};
        double bestScore = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++)
            {
                Field simulationField = new Field();
                simulationField.setCurrentPlayer(getSymbol());
                simulationField.setCellArray(field.getCellArray());
                try
                {
                    simulationField.fillCell(new Integer[]{i, j});
                }
                catch (Exception err) { continue; }
                    System.out.println("SIMULATION"); //FIXME
                    simulationField.printField();
//                    System.out.println("ACTUAL"); //FIXME
//                    field.printField();
                    double score = minimax(simulationField, false, 0);
                    if (score > bestScore)
                    {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                System.out.println(score);
                System.out.println(bestScore);

            }
        }

        return bestMove;
    }

    private double minimax(Field simulationField, boolean isMaximizing, double depth)
    {
        System.out.println("SIMULATION 1"); //FIXME
        simulationField.printField();
        Character result = simulationField.checkWinner();
        if (result == getSymbol()) {
                return 10;
        }
        else if (result == Field.getOtherSymbol(getSymbol()))
                return -10;
        else if (result == ' ')
            return 0;

        double bestScore;
        if (isMaximizing)
        {
            bestScore = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++)
                {
                    simulationField.setCurrentPlayer(symbol);
                    Integer[] coord = {i, j};
                    try { simulationField.fillCell(coord); }
                    catch (Exception err) { continue; }
                    System.out.println("SIMULATION 2"); //FIXME
                    simulationField.printField();
                    Field simulationFieldNew = new Field();
                    simulationFieldNew.setCurrentPlayer(simulationField.getCurrentPlayer());
                    simulationFieldNew.setCellArray(simulationField.getCellArray());
                        double score = minimax(simulationFieldNew,
                                false, depth + 1);
                        bestScore = Math.max(score, bestScore);
                }
            }
        }
        else
        {
            bestScore = Double.POSITIVE_INFINITY;
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++)
                {
                    simulationField.setCurrentPlayer(Field.getOtherSymbol(symbol));
                    Integer[] coord = {i, j};
                    try { simulationField.fillCell(coord); }
                    catch (Exception err) { continue; }
                    Field simulationFieldNew = new Field();
                    simulationFieldNew.setCurrentPlayer(simulationField.getCurrentPlayer());
                    simulationFieldNew.setCellArray(simulationField.getCellArray());
                    double score = minimax(simulationField,
                            true, depth + 1);
                    bestScore = Math.min(score, bestScore);
                }
            }
        }
        return bestScore;
    }

    private Integer[] scanForTwoAndClear(char soughtSymbol)
    {
//      scans for a row with 2 identical symbols and a clear cell
        ArrayList<Character> checkedLine;

        for (int i = 0; i < 3; i++)
        {
            checkedLine = field.getRow(i);
            if (!checkedLine.contains(Field.getOtherSymbol(soughtSymbol))
                    && checkedLine.contains(soughtSymbol))
            {
                int symbolCtr = 0; //counter to count how much sought symbols are there
                int clearCellCoord = 0; //coordinate of the clear cell
                for (int j = 0; j < 3; j++)
                {
                    if (checkedLine.get(j) == soughtSymbol)
                    {
                        symbolCtr++;
                    }
                    else {
                        clearCellCoord = j;
                    }
                }
                if (symbolCtr == 2)
                {
                    // if there are 2 sought symbols, there is only 1 free cell
                    return new Integer[]{i, clearCellCoord};
                }
            }
        }

        //scans for a column with 2 identical symbols and a clear cell
        for (int i = 0; i < 3; i++)
        {
            checkedLine = field.getColumn(i);
            if (!checkedLine.contains(Field.getOtherSymbol(soughtSymbol))
                    && checkedLine.contains(soughtSymbol))
            {
                int symbolCtr = 0; //counter to count how much sought symbols are there
                int clearCellCoord = 0; //coordinate of the clear cell
                for (int j = 0; j < 3; j++)
                {
                    if (checkedLine.get(j) == soughtSymbol)
                    {
                        symbolCtr++;
                    }
                    else {
                        clearCellCoord = j;
                    }
                }
                if (symbolCtr == 2)
                {
                    // if there are 2 sought symbols, there is only 1 free cell
                    return new Integer[]{clearCellCoord, i};
                }
            }
        }

        //scans for a diagonal with 2 identical symbols and a clear cell
        for (int i = 0; i < 2; i++)
        {
            checkedLine = field.getDiagonal(i);
            if (!checkedLine.contains(Field.getOtherSymbol(soughtSymbol))
                    && checkedLine.contains(soughtSymbol))
            {
                int symbolCtr = 0; //counter to count how much sought symbols are there
                int clearCellCoord = 0; //coordinate of the clear cell
                for (int j = 0; j < 3; j++)
                {
                    if (checkedLine.get(j) == soughtSymbol)
                    {
                        symbolCtr++;
                    }
                    else {
                        clearCellCoord = j;
                    }
                }
                if (symbolCtr == 2) {
                    // if there are 2 sought symbols, there is only 1 free cell
                    if (i == 0)
                        return new Integer[]{clearCellCoord, clearCellCoord};
                    else
                        return new Integer[]{clearCellCoord,2 - clearCellCoord};
                }
            }
        }

        return null;
    }

    public void setDifficulty(int userDiff) throws Exception
    {
        if (userDiff < 1 || userDiff > 4)
            throw new Exception("Enter a valid number");
        else
        this.difficulty = userDiff;
    }

    public void setSymbol(Character userSymbol)
    {
        this.symbol = userSymbol;
    }

    public Character getSymbol()
    {
        return this.symbol;
    }

    public boolean isOn()
    {
        return this.isOn;
    }

    public void setState(boolean state)
    {
        this.isOn = state;
    }

}
