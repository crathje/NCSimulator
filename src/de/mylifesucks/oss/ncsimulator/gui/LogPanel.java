/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class LogPanel extends JPanel {

    static JTextPane textPane = new JTextPane();
    static Document documentModel = textPane.getDocument();
//    static ImageIcon warningIcon;
//    static ImageIcon infoIcon;
    public static MutableAttributeSet headingStyle = new SimpleAttributeSet();
    public static MutableAttributeSet black = new SimpleAttributeSet();
    public static MutableAttributeSet red = new SimpleAttributeSet();
    public static MutableAttributeSet green = new SimpleAttributeSet();
    public static JCheckBox cb;

    public LogPanel() {
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setLayout(new BorderLayout());

        textPane.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textPane);
        add(scrollPane, BorderLayout.CENTER);

        JPanel checkBoxPanel = new JPanel(new FlowLayout());
        cb = new JCheckBox("log?");
        checkBoxPanel.add(cb);

        add(checkBoxPanel, BorderLayout.SOUTH);

        initComponents();
    }

    public static void initComponents() {
//        warningIcon = new ImageIcon(PathObject.getPath() + PathObject.getFileSep()
//                + "pics" + PathObject.getFileSep() + "bullet-red.gif");
//        infoIcon = new ImageIcon(PathObject.getPath() + PathObject.getFileSep()
//                + "pics" + PathObject.getFileSep() + "bullet-blue.gif");

        StyleConstants.setFontSize(headingStyle, 12);
        StyleConstants.setBold(headingStyle, true);
        StyleConstants.setFontFamily(headingStyle, "Monospaced");

        StyleConstants.setFontSize(black, 10);
        StyleConstants.setFontFamily(black, "Monospaced");

        StyleConstants.setFontSize(red, 10);
        StyleConstants.setFontFamily(red, "Monospaced");
        StyleConstants.setForeground(red, Color.red);

        StyleConstants.setFontSize(green, 10);
        StyleConstants.setFontFamily(green, "Monospaced");
        StyleConstants.setForeground(green, Color.blue);

        try {
            textPane.setCharacterAttributes(headingStyle, true);
            documentModel.insertString(documentModel.getLength(), "Logging started ...\n\n", headingStyle);
        } catch (BadLocationException ble) {
            System.out.println("Bad location for text insert on logpanel.");
        }
    }

    /**
     * adds the text to the lines to display.
     * warnings will be shown red. infos will
     * be shown blue.
     *
     * @param text - warning text
     * @param type - type of message
     */
    public static void giveMessage(String text, MutableAttributeSet style) {

        if (!cb.isSelected()) {
            return;
        }

        if (style == null) {
            style = black;
        }

        try {
            textPane.setCharacterAttributes(style, true);
            documentModel.insertString(documentModel.getLength(), text + "\n", style);
            textPane.setCaretPosition(documentModel.getLength());

        } catch (BadLocationException ble) {
            System.out.println("Bad location for text insert on logpanel.");
        }
    }
}
