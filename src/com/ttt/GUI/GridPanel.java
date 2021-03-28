package com.ttt.GUI;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {

    public GridPanel() {

    }

    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;

        graphics2D.setColor(Color.black);
        graphics2D.fillRect(0,0, this.getWidth(), this.getHeight());

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawLine(this.getWidth()/3, 15, this.getWidth()/3, this.getHeight()-15);
        graphics2D.drawLine(this.getWidth()/3*2, 15, this.getWidth()/3*2, this.getHeight()-15);
        graphics2D.drawLine(15, this.getHeight()/3, this.getWidth()-15, this.getHeight()/3);
        graphics2D.drawLine(15, this.getHeight()/3*2, this.getWidth()-15, this.getHeight()/3*2);
    }


}
