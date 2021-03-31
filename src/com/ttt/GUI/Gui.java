package com.ttt.GUI;

import com.ttt.Bot;
import com.ttt.Field;
import com.ttt.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Random;

// I'm not proud of this GUI

public class Gui extends JFrame implements ActionListener {

    private final Field field = Main.getField();
    private final Bot bot = Main.getBot();
    private final CellButton[][] CellButtons = new CellButton[3][3];
    private boolean isGameOn = false;

    private final JPanel mainPanel; // The whole first game screen with three panels
                                    // (labelPanel, gridPanel, buttonsPanel)

    private final JLabel mainLabel; // Top info label

    private final GridPanel gridPanel; // Panel with grid

    private final JButton settingsButton;
    private final JButton startButton;

    private final JPanel settingsPanel;
    private final JButton okButton;

    private final JRadioButton[] diffRButton;
    private final JRadioButton[] symRButton;
    private final JRadioButton[] modeRButton;

    public Gui()
    {

        field.initialFillGui(); // Writes TIC TAC TOE

        //Set up the main window
        URL iconURL = getClass().getResource("/images/logo.png");
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());
        this.setTitle("Tic-tac-toe XO");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(420,540);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.BLACK);

        //Set up the main panel
        mainPanel = new JPanel();
        this.add(mainPanel);
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(new BorderLayout());

        //Set up the label panel
        JPanel labelPanel = new JPanel();
        mainPanel.add(labelPanel, BorderLayout.NORTH);
        labelPanel.setBackground(Color.BLACK);

        //Set up the main label
        mainLabel = new JLabel("Made by Sttrom");
        mainLabel.setHorizontalAlignment(JLabel.CENTER);
        mainLabel.setVerticalAlignment(JLabel.CENTER);
        mainLabel.setForeground(Color.white);
        mainLabel.setPreferredSize(new Dimension(400,57));
        mainLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 40));
        labelPanel.add(mainLabel);

        //Set up the grid panel
        gridPanel = new GridPanel();
        mainPanel.add(gridPanel);
        gridPanel.setLayout(new GridLayout(3,3));
        gridPanel.setBackground(Color.BLACK);
        initCellButtons();

        //Set up the buttons panel
        JPanel buttonsPanel = new JPanel();
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.setBackground(Color.BLACK);
        buttonsPanel.setPreferredSize(new Dimension(420, 50));

        //Set up the buttons
        startButton = new JButton("Play!");
        buttonsPanel.add(startButton);
        startButton.setBackground(Color.WHITE);
        startButton.setPreferredSize(new Dimension(100,30));
        startButton.setFocusable(false);
        startButton.addActionListener(this);

        settingsButton = new JButton();
        buttonsPanel.add(settingsButton);
        ImageIcon settingsIcon = new ImageIcon(getClass().getResource("/images/gear.png"));
        settingsButton.setIcon(settingsIcon);
        settingsButton.addActionListener(this);
        settingsButton.setBackground(Color.WHITE);
        settingsButton.setPreferredSize(new Dimension(30,30));
        settingsButton.setFocusable(false);
        settingsButton.setVisible(true);

        //Set up the settings panel
        settingsPanel = new JPanel();
        this.add(settingsPanel);
        settingsPanel.setBackground(Color.BLACK);
        settingsPanel.setVisible(false);
        settingsPanel.setLayout(null);
        settingsPanel.setBounds(mainPanel.getBounds());

        //Set up the ok button
        okButton = new JButton("OK");
        settingsPanel.add(okButton, BorderLayout.SOUTH);
        okButton.setBackground(Color.WHITE);
        okButton.setBounds(settingsPanel.getWidth() / 2 - 50,
                settingsPanel.getHeight() - 45, 100, 30);
        okButton.setPreferredSize(new Dimension(100,30));
        okButton.setFocusable(false);
        okButton.addActionListener(this);

        //Set up the mode label
        JLabel modeLabel = new JLabel("Game mode");
        settingsPanel.add(modeLabel);
        modeLabel.setHorizontalAlignment(JLabel.CENTER);
        modeLabel.setVerticalAlignment(JLabel.CENTER);
        modeLabel.setForeground(Color.white);
        modeLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 55));
        modeLabel.setBounds(settingsPanel.getWidth() / 2 - 200,15,400,50);

        //Set up the mode radio buttons
        modeRButton = new JRadioButton[2];
        ButtonGroup modeGroup = new ButtonGroup();
        for (int i = 0; i < 2; i++ )
        {
            modeRButton[i] = new JRadioButton();
            modeRButton[i].setOpaque(false);
            modeRButton[i].setForeground(Color.white);
            modeRButton[i].setFont(new Font("Bookman Old Style", Font.PLAIN, 25));
            modeRButton[i].setBounds(15, modeLabel.getY() + 55 + 30 * i,300,30);
            modeRButton[i].setFocusable(false);
            modeRButton[i].addActionListener(this);
            modeGroup.add(modeRButton[i]);
            settingsPanel.add(modeRButton[i]);
        }
        if (bot.isOn())
            modeRButton[0].setSelected(true);
        else
            modeRButton[1].setSelected(true);
        modeRButton[0].setText("Player vs Computer");
        modeRButton[1].setText("Player vs Player");

        //Set up the difficulty label
        JLabel difficultyLabel = new JLabel("Bot's difficulty");
        settingsPanel.add(difficultyLabel);
        difficultyLabel.setHorizontalAlignment(JLabel.CENTER);
        difficultyLabel.setVerticalAlignment(JLabel.CENTER);
        difficultyLabel.setForeground(Color.white);
        difficultyLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 45));
        difficultyLabel.setBounds(settingsPanel.getWidth() / 2 - 200,
                modeRButton[1].getY() + 35,400,50);

        //Set up the difficulty radio buttons
        diffRButton = new JRadioButton[4];
        ButtonGroup diffGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++ )
        {
            diffRButton[i] = new JRadioButton();
            diffRButton[i].setOpaque(false);
            diffRButton[i].setForeground(Color.white);
            diffRButton[i].setFont(new Font("Bookman Old Style", Font.PLAIN, 25));
            diffRButton[i].setBounds(15, difficultyLabel.getY() + 50 + 30 * i,300,30);
            diffRButton[i].setFocusable(false);
            diffRButton[i].addActionListener(this);
            diffGroup.add(diffRButton[i]);
            settingsPanel.add(diffRButton[i]);
        }
        diffRButton[bot.getDifficulty()-1].setSelected(true);
        diffRButton[0].setText("Easy");
        diffRButton[1].setText("Medium");
        diffRButton[2].setText("Hard");
        diffRButton[3].setForeground(Color.RED);
        diffRButton[3].setText("Cheater");

        //Set up the bot's symbol label
        JLabel symbolLabel = new JLabel("Bot's symbol");
        settingsPanel.add(symbolLabel);
        symbolLabel.setHorizontalAlignment(JLabel.CENTER);
        symbolLabel.setVerticalAlignment(JLabel.CENTER);
        symbolLabel.setForeground(Color.white);
        symbolLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 40));
        symbolLabel.setBounds(settingsPanel.getWidth() / 2 - 150,diffRButton[3].getY() + 30,300,50);

        //Set up the symbol radio buttons
        symRButton = new JRadioButton[3];
        ButtonGroup symGroup = new ButtonGroup();
        for (int i = 0; i < 3; i++ )
        {
            symRButton[i] = new JRadioButton();
            symRButton[i].setOpaque(false);
            symRButton[i].setForeground(Color.white);
            symRButton[i].setFont(new Font("Bookman Old Style", Font.PLAIN, 25));
            symRButton[i].setBounds(15, symbolLabel.getY() + 50 + 30 * i,300,30);
            symRButton[i].setFocusable(false);
            symRButton[i].addActionListener(this);
            symGroup.add(symRButton[i]);
            settingsPanel.add(symRButton[i]);
        }

        if (Main.isBotsSymbolRandom())
        {
            symRButton[2].setSelected(true);
        }
        else if (bot.getSymbol() == Field.getSecondSymbol())
        {
            symRButton[0].setSelected(true);
        }
        else
        {
            symRButton[1].setSelected(true);
        }
        symRButton[0].setText("Always " + Field.getSecondSymbol());
        symRButton[1].setText("Always " + Field.getFirstSymbol());
        symRButton[2].setText("Random every time");

    }

    private void initCellButtons()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                CellButtons[i][j] = new CellButton(i, j);
                CellButtons[i][j].setPreferredSize(new Dimension(130,130));
                gridPanel.add(CellButtons[i][j]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton)
        {
            if (!isGameOn())
            {
                resetCellColors();
                startGame();
            }
            else
            {
                setGameOff();
                field.initialFillGui();
                updateCellButtons();
                mainLabel.setText("Made by Sttrom");
                startButton.setText("Play!");
                settingsButton.setEnabled(true);
            }
        }
        else if (e.getSource() == settingsButton)
        {
            settingsPanel.setVisible(true);
            mainPanel.setVisible(false);
        }
        else if (e.getSource() == okButton)
        {
            settingsPanel.setVisible(false);
            mainPanel.setVisible(true);
        }
        else if (e.getSource() == symRButton[0])
        {
            Main.setIsBotsSymbolRandom(false);
            bot.setSymbol(Field.getSecondSymbol());
        }
        else if (e.getSource() == symRButton[1])
        {
            Main.setIsBotsSymbolRandom(false);
            bot.setSymbol(Field.getFirstSymbol());
        }
        else if (e.getSource() == symRButton[2])
        {
            Main.setIsBotsSymbolRandom(true);
        }
        else if (e.getSource() == modeRButton[0])
        {
            bot.setState(true);
        }else if (e.getSource() == modeRButton[1])
        {
            bot.setState(false);
        }
        else
        {
            for (int i = 0; i < 4; i++)
            {
                if (e.getSource() == diffRButton[i])
                {
                    try {
                        bot.setDifficulty(i + 1);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }


    }

    public boolean isGameOn()
    {
        return isGameOn;
    }

    public void setGameOff() {isGameOn = false;}

    public void startGame()
    {
        isGameOn = true;
        field.setCurrentPlayer(Field.getFirstSymbol());
        startButton.setText("End game");
        settingsButton.setEnabled(false);
        field.clearField();
        updateCellButtons();
        if (!bot.isOn())
        {
            mainLabel.setText("Player " + field.getCurrentPlayer() + "'s turn");
        }
        else
        {
            mainLabel.setText("You vs Bot");
        }
        if (Main.isBotsSymbolRandom())  // Chooses bot's symbol if it should be chosen randomly every game
        {
            Random rand = new Random();
            int symbolNumber = rand.nextInt(2)+1;
            if (symbolNumber == 1)
            {
                bot.setSymbol(Field.getFirstSymbol());
                mainLabel.setText("Bot goes first");
            }
            else
            {
                bot.setSymbol(Field.getSecondSymbol());
                mainLabel.setText("You go first");
            }
        }
        this.update(this.getGraphics());
        botTurn();
    }

    public void updateCellButtons() // Sets GUI CellButtons' Text according to the actual field state
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                CellButtons[i][j].setText(field.getCellArray()[i][j].toString());
            }
        }
    }

    public void turn(int x, int y)
    {

        Character winner = '0';

        if (bot.isOn())
            mainLabel.setText("You vs Bot");

        if (field.getCurrentPlayer() != bot.getSymbol() || !bot.isOn()) {
            playerTurn(x, y);
            winner = field.checkWinner();
        }

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
            CellButtons[x][y].setText(field.getCurrentPlayer().toString());
            CellButtons[x][y].update(CellButtons[x][y].getGraphics());
            field.changePlayer();
        }
        catch (Exception err){ return; }
        if (!bot.isOn())
        {
            mainLabel.setText("Player " + field.getCurrentPlayer() + "'s turn");
        }

    }

    public void botTurn()
    {
        while(field.getCurrentPlayer() == bot.getSymbol())
        {
            try {
                field.fillCell(bot.turn());
                updateCellButtons();
                field.changePlayer();
            }
            catch (Exception e) { System.out.println(e.getMessage()); }
        }
    }

    public void end(Character winner)
    {
        settingsButton.setEnabled(true);
        if (winner == ' ')
        {
            mainLabel.setText("Draw!");
        }
        else
            {
            if (!bot.isOn())
                mainLabel.setText("The winner is " + winner + "!");
            else
            {
                if (winner == bot.getSymbol())
                    mainLabel.setText("Bot won!");
                else
                    mainLabel.setText("You won!");
            }
                Integer[][] winningCombination = field.getWinningCombination();
                for(int i = 0; i < 3; i++)
                {
                    CellButtons[winningCombination[i][0]][winningCombination[i][1]].setForeground(Color.RED);
                }
        }

        startButton.setText("Restart!");
    }

    public void resetCellColors()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                CellButtons[i][j].setForeground(Color.WHITE);
            }
        }
    }

}
