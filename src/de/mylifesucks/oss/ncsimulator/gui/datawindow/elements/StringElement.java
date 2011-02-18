/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.gui.datawindow.elements;

import de.mylifesucks.oss.graphichelpers.DrawStringHelpers;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.prefs.Preferences;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class StringElement extends DrawableElement {

    public static final String SPRINTFSTRING = "sprintf String";
    public static final String DECIMALS = "Number of decimals after coma";
    public static final String MAXSIZE = "Draw Text centered & as big as possible (computed before transformations)";
    public static final String FONTNAME = "Name of the Font";
    public static final String STANDARDFONT = "Dialog";
    public static final String TEXTXPOS = "X position of the text";
    public static final String TEXTXPOSPERCENT = "X position is % of width";
    public static final String TEXTYPOS = "Y position of the text";
    public static final String TEXTYPOSPERCENT = "Y position is % of height";
    private static ArrayList<String> fontMap;
    private c_intElement value;
    private c_intElement textSize;
    private c_intElement textXPos;
    private c_intElement textYPos;
    private JTextField sprintfTextField;
    private JTextField decimalsTextField;
    private JComboBox fontNames;
    private JCheckBox maxSize;
    private JCheckBox textXPosPercent;
    private JCheckBox textYPosPercent;

    public StringElement(final Preferences nodePref, final String name) {
        super(nodePref, name);
        value = new c_intElement(myPref, myName + "value");
        textSize = new c_intElement(myPref, myName + "textSize");
        textXPos = new c_intElement(myPref, myName + "textXPos");
        textYPos = new c_intElement(myPref, myName + "textYPos");


        initMyComponent();
    }

    private String getTextValue() {
        return myPref.get(myName + SPRINTFSTRING, "value: %s");
    }

    private int getDecimals() {
        return myPref.getInt(myName + DECIMALS, 0);
    }

    private boolean getMaxSize() {
        return myPref.getBoolean(myName + MAXSIZE, true);
    }

    private boolean getTextXPosPercent() {
        return myPref.getBoolean(myName + TEXTXPOSPERCENT, true);
    }

    private boolean getTextYPosPercent() {
        return myPref.getBoolean(myName + TEXTYPOSPERCENT, true);
    }

    @Override
    protected void initMyComponent() {
        super.initMyComponent();
        JPanel panel = (JPanel) myComponent;

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(new JLabel(SPRINTFSTRING), gbc);
        gbc.gridy++;

        sprintfTextField = new JTextField(getTextValue());
        sprintfTextField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println(e);
            }
        });
        panel.add(sprintfTextField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel(FONTNAME), gbc);


        if (fontMap == null) {
            GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font[] af = e.getAllFonts();
            String[] afn = new String[af.length];
            int i = 0;
            for (Font f : af) {
                afn[i++] = f.getName();
            }
            Arrays.sort(afn);
            fontMap = new ArrayList<String>(afn.length);
            for (String s : afn) {
                fontMap.add(s);
            }
        }
        fontNames = new JComboBox(fontMap.toArray());
        fontNames.setSelectedItem(myPref.get(myName + FONTNAME, STANDARDFONT));
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(fontNames, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel(DECIMALS), gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        decimalsTextField = new JTextField(String.valueOf(getDecimals()));
        panel.add(decimalsTextField, gbc);



        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Monitored Value"), gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(value.getChangeComponent(), gbc);




        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Textsize"), gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(textSize.getChangeComponent(), gbc);

        maxSize = new JCheckBox(MAXSIZE);
        gbc.gridy++;
        gbc.gridx = 0;
        maxSize.setSelected(getMaxSize());
        panel.add(maxSize, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Position"), gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(textXPos.getChangeComponent(), gbc);

        textXPosPercent = new JCheckBox(TEXTXPOSPERCENT);
        gbc.gridy++;
        gbc.gridx = 0;
        textXPosPercent.setSelected(getTextXPosPercent());
        panel.add(textXPosPercent, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(textYPos.getChangeComponent(), gbc);

        textYPosPercent = new JCheckBox(TEXTYPOSPERCENT);
        gbc.gridy++;
        gbc.gridx = 0;
        textYPosPercent.setSelected(getTextYPosPercent());
        panel.add(textYPosPercent, gbc);

    }

    protected void extraPersist() {
//        System.out.println("extraPersists: " + myName + getClass().getSimpleName());
        myPref.put(myName + SPRINTFSTRING, sprintfTextField.getText());
        value.persist(myName + "value");
        textSize.persist(myName + "textSize");
        textXPos.persist(myName + "textXPos");
        textYPos.persist(myName + "textYPos");

        try {
            int i = Integer.valueOf(decimalsTextField.getText());
            myPref.putInt(myName + DECIMALS, i);
        } catch (NumberFormatException nfe) {
        }

        myPref.putBoolean(myName + MAXSIZE, maxSize.isSelected());
        myPref.putBoolean(myName + TEXTXPOSPERCENT, textXPosPercent.isSelected());
        myPref.putBoolean(myName + TEXTYPOSPERCENT, textYPosPercent.isSelected());
        if (fontNames.getSelectedIndex() < 0 || fontNames.getSelectedIndex() > fontNames.getSelectedObjects().length) {
            fontNames.setSelectedItem(0);
        }
        myPref.put(myName + FONTNAME, fontNames.getSelectedObjects()[0].toString());
    }

    public String valueToString() {
        String drawString = getTextValue();
        try {
            String me = String.valueOf(value.getValue());
            int decimals = getDecimals();

            if (me.contains(".")) {
                while (me.substring(me.indexOf(".")).length() < decimals + 1) {
                    me = me + "0";
                }
                if (decimals == 0) {
                    me = me.substring(0, me.indexOf("."));
                }
            } else {
                while (me.length() < decimals + 1) {
                    me = "0" + me;
                }
                if (decimals > 0) {
                    me = me.substring(0, me.length() - decimals) + "." + me.substring(me.length() - decimals);
                }
            }

            // nasty double unprecision
            if (decimals > 0 && me.substring(me.indexOf(".")).length() > decimals) {
                me = me.substring(0, me.indexOf(".") + decimals + 1);
            }

            //System.out.println(me);
            drawString = String.format(getTextValue(), me);
        } catch (Exception e) {
            System.out.println("" + e);
        }
        return drawString;
    }

    @Override
    public void drawTo(final Graphics2D g2d, final Dimension d) {
//        Color c = getForegroundColor(true);
        g2d.setColor(getForegroundColor(true));

        g2d.setFont(new Font(fontNames.getSelectedItem().toString(), Font.PLAIN, (int) textSize.getValue()));
        String drawString = valueToString();
        if (getMaxSize()) {
            DrawStringHelpers.drawStringBiggest(g2d, d, drawString);
        } else {
            //DrawStringHelpers.drawStringCentered(g2d, d, drawString, (int) textSize.getValue());
            DrawStringHelpers.drawStringAtPos(g2d, d, drawString,
                    (int) textSize.getValue(),
                    textXPos.getValue(), getTextXPosPercent(),
                    textYPos.getValue(), getTextYPosPercent());
        }
    }
}
