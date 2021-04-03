package com.ttt.GUI;

import com.ttt.Field;
import com.ttt.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CellButton extends JButton implements ActionListener {

    Field field = Main.getField();

    // Cell's coordinates
    int x;
    int y;

    public CellButton(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
        this.setForeground(Color.WHITE);
        this.setFocusable(false);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFont(new Font("Comic Sans MS", Font.PLAIN, 100));
        this.setText(field.getCellArray()[this.x][this.y].toString());
        this.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (Main.getGui().isGameOn())
        {
            Main.getGui().turn(this.x, this.y);
        }
    }
}
