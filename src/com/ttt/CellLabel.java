package com.ttt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CellLabel extends JLabel implements MouseListener {

    Field field = Main.getField();
    Bot bot = Main.getBot();
    int x;
    int y;

    public CellLabel(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
        this.setForeground(Color.white);
        this.setFont(new Font("Comic Sans MS", Font.PLAIN, 100));
        this.setText(field.getCellArray()[this.x][this.y].toString());
        this.addMouseListener(this);

    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        this.setText("X");
        if (field.getCurrentPlayer() != bot.getSymbol())
        {
            if (field.getCellArray()[this.x][this.y] == ' ')
            {
//                try{field.fillCell(new Integer[]{this.x, this.y});}
//                catch (Exception err){}
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
