package com.ttt;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The GUI could be better

public class Gui extends JFrame implements ActionListener {

    private final Field field = Main.getField();
    private final Bot bot = Main.getBot();
    private final GridPanel gridPanel;
    private final JPanel menuPanel;
    private final CellLabel[][] cellLabels= new CellLabel[3][3];
    private boolean isGameOn = false;
    private final JButton settingsButton;
    private final JButton startButton;

    public Gui()
    {

        this.setTitle("Tic-tac-toe XO");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(420,520);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        gridPanel = new GridPanel();
        this.add(gridPanel);
        gridPanel.setLayout(new GridLayout(3,3));
        gridPanel.setBackground(Color.BLACK);
        initCellLabels();

        menuPanel = new JPanel();
        this.add(menuPanel, BorderLayout.SOUTH);
        menuPanel.setBackground(Color.BLACK);

        startButton = new JButton();
        menuPanel.add(startButton);
        startButton.setBackground(Color.WHITE);
        startButton.setPreferredSize(new Dimension(100,30));
        startButton.setFocusable(false);
        startButton.addActionListener(this);
        startButton.setText("Play!");
        startButton.setVisible(true);

        settingsButton = new JButton();
        menuPanel.add(settingsButton);
        settingsButton.setBackground(Color.WHITE);
        settingsButton.setPreferredSize(new Dimension(30,30));
        settingsButton.setFocusable(false);
        try {
            Image img = ImageIO.read(getClass().getResource("images/gear_s.png"));
            settingsButton.setIcon(new ImageIcon(img)); // FIXME
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        settingsButton.setVisible(true);

    }

    private void initCellLabels()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                cellLabels[i][j] = new CellLabel(i, j);
                cellLabels[i][j].setPreferredSize(new Dimension(130,130));
                gridPanel.add(cellLabels[i][j]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton)
        {
            startGame();
        }
    }

    public boolean isGameOn()
    {
        return isGameOn;
    }

    public void setGameOff() {isGameOn = false;}

    public void startGame()
    {
        bot.setState(true);
        bot.setSymbol(Field.getSecondSymbol());
        try{bot.setDifficulty(4);}
        catch (Exception err){ System.out.println(err.getMessage()); }
        isGameOn = true;
        field.setCurrentPlayer(Field.getFirstSymbol());
        startButton.setText("Restart");
        settingsButton.setEnabled(false);
        field.clearField();
        updateCellLabels();
    }

    public void updateCellLabels()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                cellLabels[i][j].setText(field.getCellArray()[i][j].toString());
            }
        }
    }

    public void turn(int x, int y)
    {

        Character winner;

        playerTurn(x, y);
        winner = field.checkWinner();

        if (winner != '0' && bot.getDifficulty() != 4)
        {
            setGameOff();
            end(winner);
            return;
        }

        if (field.getCurrentPlayer() == bot.getSymbol() && bot.isOn() && isGameOn())
        {
            botTurn();
        }
        winner = field.checkWinner();

        if (winner != '0')
        {
            setGameOff();
            end(winner);
        }
    }

    public void playerTurn(int x, int y)
    {
        try
        {
            field.fillCell(new Integer[]{x, y});
            cellLabels[x][y].setText(field.getCurrentPlayer().toString());
            cellLabels[x][y].update(cellLabels[x][y].getGraphics());
            field.changePlayer();
        }
        catch (Exception err){System.out.println(err.getMessage()); }


    }

    public void botTurn()
    {
        try
        {
            field.fillCell(bot.turn());
            Thread.sleep(400);
            updateCellLabels();
            field.changePlayer();
        }
        catch (Exception err) { System.out.println(err.getMessage()); }

    }

    public void end(Character winner)
    {
        System.out.println("The winner is " + winner);
    }

}
