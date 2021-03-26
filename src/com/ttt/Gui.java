package com.ttt;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

    private Field field = Main.getField();
    private Bot bot = Main.getBot();
    private final GridPanel gridPanel;
    private final JPanel menuPanel;
    private CellLabel[][] cellLabels= new CellLabel[3][3];

    public Gui()
    {

        this.setTitle("Tic-tac-toe XO");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setResizable(false);
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


        JButton startButton = new JButton();
        menuPanel.add(startButton);
        startButton.setBackground(Color.WHITE);
        startButton.setPreferredSize(new Dimension(100,30));
        startButton.setFocusable(false);
        startButton.setText("Play!");
        startButton.setVisible(true);


        JButton settingsButton = new JButton();
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

}
