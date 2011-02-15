/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.datatypes;

import java.awt.GridBagConstraints;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * Datatype for a String since char[] -> String is not that easy as in C
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class c_string extends c_int {

    public c_string(String name, int chars, String initial) {
        this.name = name;
        this.length = chars * 8;
        valueField = new JTextField(initial);
    }

    @Override
    public synchronized void addToPanel(JComponent panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        panel.add(getNameLabel(), gbc);

        gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        panel.add(valueField, gbc);

        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy++;
    }

    @Override
    public void setValue(long value, boolean notice) {
    }

    @Override
    public void loadFromInt(int[] RxdBuffer, int pRxData) {
        if (RxdBuffer.length > pRxData + getLength() / 8) {
            String v = "";
            for (int i = 0; i < getLength() / 8; i++) {
                if (RxdBuffer[pRxData + i] == 0) {
                    break;
                }
                v += (char) RxdBuffer[pRxData + i];
            }
            valueField.setText(v);
        }
    }

    public void setValue(String text) {
        ((JTextField) valueField).setText(text);
    }

    @Override
    protected void initComponent() {
    }

    @Override
    public int[] getAsInt() {
        int[] ret = new int[length / 8];
        String text = ((JTextField) valueField).getText();
        int i = 0;
        while (i < text.length() && i < ret.length) {
            ret[i] = text.toCharArray()[i];
            i++;
        }

        while (i < ret.length) {
            ret[i] = '\0';
            i++;
        }
        return ret;
    }
}

