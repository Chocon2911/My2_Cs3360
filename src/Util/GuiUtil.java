package Util;

import java.awt.*;
import javax.swing.*;

public abstract class GuiUtil
{
    //==========================================Variable==========================================
    // Frame
    protected int frameWidth = 500;
    protected int frameHeight = 600;

    // Strut
    protected int horizontalStrut = 20;
    protected int verticalStrut = 20;

    // Label
    protected int normalLabelWidth = 100;
    protected int normalLabelHeight = 20;

    // Panel
    protected int panelTextFieldWidth = 300;
    protected int panelTextFieldHeight = 25;

    // Button
    protected int bigButtonWidth = 200;
    protected int bigButtonHeight = 50;

    protected int smallButtonWidth = 100;
    protected int smallButtonHeight = 25;

    // Text Field
    protected int textFieldAmount = 20;

    // Font
    protected int bigTitleSize = 50;
    protected int normalTitleSize = 40;
    protected int smallTitleSize = 30;

    protected int normalTextSize = 15;

    //===========================================Method===========================================
    protected void setFixedSize(JComponent component, int width, int height)
    {
        component.setPreferredSize(new Dimension(width, height));
        component.setMaximumSize(new Dimension(width, height));
        component.setMinimumSize(new Dimension(width, height));
    }

    protected void setAlignmentCenter(JComponent component)
    {
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
        component.setAlignmentY(Component.CENTER_ALIGNMENT);
    }

    protected void setAlignmentCenterLeft(JComponent component)
    {
        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        component.setAlignmentY(Component.CENTER_ALIGNMENT);
    }

    protected void setAlignmentCenterRight(JComponent component)
    {
        component.setAlignmentX(Component.RIGHT_ALIGNMENT);
        component.setAlignmentY(Component.CENTER_ALIGNMENT);
    }

    protected JButton createButton(String name, int width, int height)
    {
        JButton button = new JButton(name);
        this.setFixedSize(button, width, height);
        return button;
    }
}
