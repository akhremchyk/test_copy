package com.ttt;

import java.util.ArrayList;
import java.util.Random;

public class Bot {

    private Character symbol = Field.getSecondSymbol();
    private boolean isOn; // indicates whether the bot is turned on or not
    private final Field field = Main.getField();
    private int difficulty = 3;



    public Integer[] turn()
    {
        if (difficulty == 1)
            return easyDifficulty();
        else if (difficulty == 2)
            return mediumDifficulty();
        else if (difficulty == 3)
            return hardDifficulty();
        else
            return cheaterDifficulty();

    }

    private Integer[] easyDifficulty()
    {
        // Picks random cell every time
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
        // Minimax algorithm using alpha-beta pruning

        Integer[] bestMove = {0, 0};
        double bestScore = Double.NEGATIVE_INFINITY;
        double cell5Score = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                field.setCurrentPlayer(symbol);
                try
                {
                    field.fillCell(new Integer[]{i, j});
                }
                catch (Exception err) { continue; }
                double score = minimax(field, false, 0,
                        Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
                if (score > bestScore)
                {
                    bestScore = score;
                    bestMove[0] = i;
                    bestMove[1] = j;
                }
                if (i == j && i == 1)
                {
                    cell5Score = score; // to choose central cell if it's not worse than the current best choise
                }

                field.clearCell(new Integer[] {i,j});
            }
        }
        field.setCurrentPlayer(symbol);
        if (cell5Score >= bestScore)
            return new Integer[] {1,1};
        else
        return bestMove;
    }

    private Integer[] cheaterDifficulty()
    {
        // Bot scans for lines with two identical symbols and fills the third cell.
        // If there are two bot's symbols it fills the third cell regardless of whether it's empty or not.
        // If there are none such cells, it fills cell using minimax algorithm.

        Integer[] botCell;

        botCell = scanForTwo(field.getCurrentPlayer());
        if (botCell != null) {
            try{Thread.sleep(500);}
            catch (Exception err){return botCell;}
            return botCell;
        } else {
            botCell = scanForTwoAndClear(field.getOtherPlayer());
        }

        if (botCell != null) {
            return  botCell;
        }
        else {
            return hardDifficulty();
        }
    }

    private double minimax(Field fieldSimulation, boolean isMaximizing, double depth, double alpha, double beta)
    {
        Character result = fieldSimulation.checkWinner();
        if (result == getSymbol()) {
                return 10 - depth;
        }
        else if (result == Field.getOtherSymbol(getSymbol())) {
            return -10 + depth;
        }
        else if (result == ' '){
            return 0;
        }

        double bestScore;
        if (isMaximizing)
        {
            bestScore = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    fieldSimulation.setCurrentPlayer(symbol);
                    Integer[] coord = {i, j};
                    try { fieldSimulation.fillCell(coord); }
                    catch (Exception err) { continue; }
                    double score = minimax(fieldSimulation,
                                false, depth + 1, alpha, beta);
                    bestScore = Math.max(score, bestScore);
                    alpha = Math.max(alpha, score);
                    fieldSimulation.clearCell(new Integer[] {i,j});
                    if (alpha >= beta)
                        return bestScore;
                }
            }
        }
        else
        {
            bestScore = Double.POSITIVE_INFINITY;
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++)
                {
                    fieldSimulation.setCurrentPlayer(Field.getOtherSymbol(symbol));
                    Integer[] coord = {i, j};
                    try { fieldSimulation.fillCell(coord); }
                    catch (Exception err) { continue; }
                    double score = minimax(fieldSimulation,
                            true, depth + 1, alpha, beta);
                    bestScore = Math.min(score, bestScore);
                    beta = Math.min(beta, score);
                    fieldSimulation.clearCell(new Integer[] {i,j});
                    if (alpha >= beta)
                        return bestScore;
                }
            }
        }
        return bestScore;
    }

    private Integer[] scanForTwo(char soughtSymbol)
    {
//      scans for a row with 2 identical symbols
        ArrayList<Character> checkedLine;

        for (int i = 0; i < 3; i++)
        {
            checkedLine = field.getRow(i);
            if (checkedLine.contains(soughtSymbol))
            {
                int symbolCtr = 0; //counter to count how much sought symbols are there
                int otherCellCoord = 0; //coordinate of a cell without the sought symbol
                for (int j = 0; j < 3; j++)
                {
                    if (checkedLine.get(j) == soughtSymbol)
                    {
                        symbolCtr++;
                    }
                    else {
                        otherCellCoord = j;
                    }
                }
                if (symbolCtr == 2)
                {
                    // if there are 2 sought symbols, there is only 1 cell with the other symbol
                    Integer[] soughtCell = new Integer[]{i, otherCellCoord};
                    field.clearCell(soughtCell);
                    return soughtCell;
                }
            }
        }

        //scans for a column with 2 identical symbols
        for (int i = 0; i < 3; i++)
        {
            checkedLine = field.getColumn(i);
            if (checkedLine.contains(soughtSymbol))
            {
                int symbolCtr = 0; //counter to count how much sought symbols are there
                int otherCellCoord = 0; //coordinate of the clear cell
                for (int j = 0; j < 3; j++)
                {
                    if (checkedLine.get(j) == soughtSymbol)
                    {
                        symbolCtr++;
                    }
                    else {
                        otherCellCoord = j;
                    }
                }
                if (symbolCtr == 2)
                {
                    // if there are 2 sought symbols, there is only 1 cell with the other symbol
                    Integer[] soughtCell = new Integer[]{otherCellCoord, i};
                    field.clearCell(soughtCell);
                    return soughtCell;
                }
            }
        }

        //scans for a diagonal with 2 identical symbols
        for (int i = 0; i < 2; i++)
        {
            checkedLine = field.getDiagonal(i);
            if (checkedLine.contains(soughtSymbol))
            {
                int symbolCtr = 0; //counter to count how much sought symbols are there
                int otherCellCoord = 0; //coordinate of the clear cell
                for (int j = 0; j < 3; j++)
                {
                    if (checkedLine.get(j) == soughtSymbol)
                    {
                        symbolCtr++;
                    }
                    else {
                        otherCellCoord = j;
                    }
                }
                if (symbolCtr == 2) {
                    // if there are 2 sought symbols, there is only 1 free cell
                    Integer[] soughtCell;
                    if (i == 0) {
                        soughtCell = new Integer[]{otherCellCoord, otherCellCoord};
                    }
                    else {
                        soughtCell = new Integer[]{otherCellCoord, 2 - otherCellCoord};
                    }
                    field.clearCell(soughtCell);
                    return soughtCell;
                }
            }
        }

        return null;
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

    public int getDifficulty() { return difficulty; }

}
