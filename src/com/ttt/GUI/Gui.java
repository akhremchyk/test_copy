package com.ttt.GUI;

import com.ttt.Bot;
import com.ttt.Field;
import com.ttt.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// I'm not proud of this GUI

public class Gui extends JFrame implements ActionListener {

    private final Field field = Main.getField();
    private final Bot bot = Main.getBot();
    private final CellLabel[][] cellLabels= new CellLabel[3][3];
    private boolean isGameOn = false;

    private final JPanel mainPanel; // The whole first game screen with two panels (gridPanel, buttonsPanel)
    private final GridPanel gridPanel; // Panel with grid
    private final JPanel buttonsPanel; // Panel with buttons
    private final JButton settingsButton;
    private final JButton startButton;

    private final JPanel settingsPanel;
    private final JButton okButton;
    private final JLabel difficultyLabel;
    private final JLabel symbolLabel;
    private final JRadioButton[] diffRButton;
    private final JRadioButton[] symRButton;
    private final ButtonGroup diffGroup;
    private final ButtonGroup symGroup;

    public Gui()
    {

        field.initialFillGui();

        //Set up the main window
        this.setTitle("Tic-tac-toe XO");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(420,520);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.BLACK);

        //Set up the main panel
        mainPanel = new JPanel();
        this.add(mainPanel);
        mainPanel.setLayout(new BorderLayout());

        //Set up the grid panel
        gridPanel = new GridPanel();
        mainPanel.add(gridPanel);
        gridPanel.setLayout(new GridLayout(3,3));
        gridPanel.setBackground(Color.BLACK);
        initCellLabels();

//        Set up the buttons panel
        buttonsPanel = new JPanel();
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.setBackground(Color.BLACK);

        //Set up the buttons
        startButton = new JButton("Play!");
        buttonsPanel.add(startButton);
        startButton.setBackground(Color.WHITE);
        startButton.setPreferredSize(new Dimension(100,30));
        startButton.setFocusable(false);
        startButton.addActionListener(this);

        settingsButton = new JButton();
        buttonsPanel.add(settingsButton);
        settingsButton.addActionListener(this);
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
                settingsPanel.getHeight() - 35, 100, 30);
        okButton.setPreferredSize(new Dimension(100,30));
        okButton.setFocusable(false);
        okButton.addActionListener(this);

        //Set up the difficulty label
        difficultyLabel = new JLabel("Bot's difficulty");
        settingsPanel.add(difficultyLabel);
        difficultyLabel.setHorizontalAlignment(JLabel.CENTER);
        difficultyLabel.setVerticalAlignment(JLabel.CENTER);
        difficultyLabel.setForeground(Color.white);
        difficultyLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 45));
        difficultyLabel.setBounds(settingsPanel.getWidth() / 2 - 200,30,400,50);

        //Set up the difficulty radio buttons
        diffRButton = new JRadioButton[4];
        diffGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++ )
        {
            diffRButton[i] = new JRadioButton();
            diffRButton[i].setOpaque(false);
            diffRButton[i].setForeground(Color.white);
            diffRButton[i].setFont(new Font("Bookman Old Style", Font.PLAIN, 25));
            diffRButton[i].setBounds(15,90 + 30 * i,300,30);
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
        symbolLabel = new JLabel("Bot's symbol");
        settingsPanel.add(symbolLabel);
        symbolLabel.setHorizontalAlignment(JLabel.CENTER);
        symbolLabel.setVerticalAlignment(JLabel.CENTER);
        symbolLabel.setForeground(Color.white);
        symbolLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 40));
        symbolLabel.setBounds(settingsPanel.getWidth() / 2 - 150,220,300,50);

        //Set up the difficulty radio buttons
        symRButton = new JRadioButton[3];
        symGroup = new ButtonGroup();
        for (int i = 0; i < 3; i++ )
        {
            symRButton[i] = new JRadioButton();
            symRButton[i].setOpaque(false);
            symRButton[i].setForeground(Color.white);
            symRButton[i].setFont(new Font("Bookman Old Style", Font.PLAIN, 25));
            symRButton[i].setBounds(15,280 + 30 * i,300,30);
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
//            isGameOn = true;
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
        bot.setState(true);
        isGameOn = true;
        field.setCurrentPlayer(Field.getFirstSymbol());
        startButton.setText("Restart");
        settingsButton.setEnabled(false);
        field.clearField();
        updateCellLabels();
        this.update(this.getGraphics());
        botTurn();
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

        Character winner = '0';

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
            cellLabels[x][y].setText(field.getCurrentPlayer().toString());
            cellLabels[x][y].update(cellLabels[x][y].getGraphics());
            field.changePlayer();
        }
        catch (Exception err){System.out.println(err.getMessage()); }


    }

    public void botTurn()
    {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(field.getCurrentPlayer() == bot.getSymbol())
        {
            try {
                field.fillCell(bot.turn());
                updateCellLabels();
                field.changePlayer();
            }
            catch (Exception e) { e.getMessage(); }
        }
    }

    public void end(Character winner)
    {
        settingsButton.setEnabled(true);
        System.out.println("The winner is " + winner);
        startButton.setText("Play!");
    }

}
