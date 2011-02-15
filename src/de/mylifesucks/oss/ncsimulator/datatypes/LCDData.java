/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.datatypes;

import java.awt.Font;
import java.awt.GridBagConstraints;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * LCD-Data struct
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class LCDData extends c_int {

    public static String text = "";
    public static int MaxMenuItem = 0;
    public boolean locked = false;

    @Override
    public int[] getAsInt() {
        int[] ret = new int[80];
        String s = text;
        if (s.length() > 80) {
            s = s.substring(0, 80);
        }

        int i = 0;
        for (char c : s.toCharArray()) {
            ret[i++] = (int) c;
        }
        for (; i < ret.length; i++) {
            ret[i] = (int) ' ';
        }


        return ret;
        //u8 MenuItem, u8 MaxMenuItem, char[80] Display Text
    }
    protected JTextField rows[] = new JTextField[4];

    @Override
    protected void initComponent() {
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new JTextField(20);
            rows[i].setDocument(new ValueDocument());
            rows[i].setFont(new Font(Font.MONOSPACED, Font.BOLD, 26));
        }
        setText("NC Sim running!     nothing interesting going on here...    ...seriously!");
    }

    @Override
    public synchronized void addToPanel(JComponent panel, GridBagConstraints gbc) {
        if (rows[0] == null) {
            initComponent();
        }
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        for (int i = 0; i < rows.length; i++) {

            panel.add(rows[i], gbc);
            gbc.gridy++;
        }
        gbc.gridwidth = 1;
    }

    public class ValueDocument extends PlainDocument {

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            super.insertString(offs, str, a);
            if (!locked) {
                composeText();
            }
        }

        @Override
        public void remove(int offs, int len) throws BadLocationException {
            super.remove(offs, len);
            if (!locked) {
                composeText();
            }
        }
    }

    protected void composeText() {
        text = "";
        for (int i = 0; i < rows.length; i++) {
            if (rows[i].getText().length() > 20) {
                text += rows[i].getText().substring(0, 20);
            } else {
                text += rows[i].getText();
                for (int pad = rows[i].getText().length(); pad < 20; pad++) {
                    text += " ";
                }
            }
        }
        //setText(text);

    }

    public void setText(String textset) {
        if (textset.length() > 80) {
            textset = textset.substring(0, 80);
        }
        text = textset;

        for (int i = 0; i < rows.length; i++) {
            String set = "";
            for (int x = 0; x < 20; x++) {
                int pos = i * 20 + x;
                if (pos < text.length()) {
                    set += text.charAt(pos);
                } else {
                    set += ' ';
                }
            }
            locked = true;
            rows[i].setText(set);
            locked = false;
        }

    }
}
