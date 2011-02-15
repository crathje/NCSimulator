/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.gui;

import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage;
import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage.UART_CONNECTION;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class StatusBar extends JPanel {

    public JLabel uartMode;
    public colorToggleLabel uartRX;
    public colorToggleLabel uartTX;
    static Font myFont = new Font("Monospaced", Font.BOLD, 20);
    JButton changeUartButton;

    public StatusBar() {
        super(new FlowLayout());

        uartMode = new JLabel(DataStorage.UART.name());
        uartMode.setFont(myFont);

        changeUartButton = new JButton("UART:");

//        JLabel l = new JLabel("UART: ");
        changeUartButton.setFont(myFont.deriveFont(myFont.getSize() / 1.5f));
        changeUartButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                List<UART_CONNECTION> l = Arrays.asList(DataStorage.UART.values());
                DataStorage.setUART(l.get((l.indexOf(DataStorage.UART) + 1) % l.size()));
            }
        });
        add(changeUartButton);
        add(Box.createHorizontalStrut(10));

        add(uartMode);
        add(Box.createHorizontalStrut(100));

        uartRX = new colorToggleLabel("RX", new Color[]{Color.green.darker().darker(), Color.black.darker().darker()});
        add(uartRX);

        add(Box.createHorizontalStrut(100));
        uartTX = new colorToggleLabel("TX", new Color[]{Color.red.darker().darker(), Color.black.darker().darker()});
        add(uartTX);
    }

    public static class colorToggleLabel extends JLabel {

        Color colors[];
        int index = 0;
        public static final char bar[] = new char[]{'-', '\\', '|', '/'};
        int charindex = 0;
        String label;

        public colorToggleLabel(String text, Color colors[]) {
            super(text);
            this.setFont(myFont);
            this.label = text;
            this.colors = colors;
            this.setForeground(colors[index]);
            toggle();
        }

        public void toggle() {
            index %= colors.length;
            charindex %= bar.length;
            //this.setForeground(colors[index]);
            this.setText(label + " " + bar[charindex]);
            index++;
            charindex++;
        }
    };
}
