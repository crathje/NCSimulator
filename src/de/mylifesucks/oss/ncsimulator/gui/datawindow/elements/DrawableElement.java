/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.gui.datawindow.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public abstract class DrawableElement extends AbstractElement {

    //
    protected JButton foregroundTransparencyButton;
    protected JButton backgroundTransparencyButton;
    protected JButton foregroundColorButton;
    protected JButton backgroundColorButton;
    protected JPanel foregroundColorLabel;
    protected JPanel backgroundColorLabel;
    protected JOptionPane customOptionPane;
    protected GridBagConstraints gbc;
    //
    public static final String FOREGROUND = "Foreground";
    public static final String FOREGROUNDCOLOR = FOREGROUND + " Color";
    public static final String FOREGROUNDCOLORR = FOREGROUNDCOLOR + "RED";
    public static final String FOREGROUNDCOLORG = FOREGROUNDCOLOR + "GREEN";
    public static final String FOREGROUNDCOLORB = FOREGROUNDCOLOR + "BLUE";
    public static final String FOREGROUNDTRANSPARENCY = FOREGROUND + " Transparency";
    public static final String BACKGROUND = "Background";
    public static final String BACKGROUNDCOLOR = BACKGROUND + " Color";
    public static final String BACKGROUNDCOLORR = BACKGROUNDCOLOR + "RED";
    public static final String BACKGROUNDCOLORG = BACKGROUNDCOLOR + "GREEN";
    public static final String BACKGROUNDCOLORB = BACKGROUNDCOLOR + "BLUE";
    public static final String BACKGROUNDTRANSPARENCY = BACKGROUND + " Transparency";
    //
    private Color foreGroundColor;
    private JButton src;

    public abstract void drawTo(final Graphics2D g2d, final Dimension d);

    public DrawableElement(final Preferences rootPref, final String name) {
        super(rootPref, name);
        foreGroundColor = getForegroundColor(false);
    }

    protected Color getForegroundColor(final boolean withAlpha) {
        if (withAlpha) {
            return new Color(myPref.getInt(myName + FOREGROUNDCOLORR, 255),
                    myPref.getInt(myName + FOREGROUNDCOLORG, 0),
                    myPref.getInt(myName + FOREGROUNDCOLORB, 0),
                    getForegroundTransparency());
        }
        return new Color(myPref.getInt(myName + FOREGROUNDCOLORR, 255),
                myPref.getInt(myName + FOREGROUNDCOLORG, 0),
                myPref.getInt(myName + FOREGROUNDCOLORB, 0));
    }

    protected void setForegroundColor(final Color color) {
        foreGroundColor = color;
        myPref.putInt(myName + FOREGROUNDCOLORR, color.getRed());
        myPref.putInt(myName + FOREGROUNDCOLORG, color.getGreen());
        myPref.putInt(myName + FOREGROUNDCOLORB, color.getBlue());
        if (foregroundColorLabel != null) {
            foregroundColorLabel.setBackground(color);
        }
    }

    protected int getForegroundTransparency() {
        return myPref.getInt(myName + FOREGROUNDTRANSPARENCY, 255);
    }

    protected void setForegroundTransparency(final int value) {
        myPref.putInt(myName + FOREGROUNDTRANSPARENCY, value);
    }

    protected Color getBackgroundColor() {
        return new Color(myPref.getInt(myName + BACKGROUNDCOLORR, 0),
                myPref.getInt(myName + BACKGROUNDCOLORG, 0),
                myPref.getInt(myName + BACKGROUNDCOLORB, 0));
    }

    protected void setBackgroundColor(final Color color) {
        myPref.putInt(myName + BACKGROUNDCOLORR, color.getRed());
        myPref.putInt(myName + BACKGROUNDCOLORG, color.getGreen());
        myPref.putInt(myName + BACKGROUNDCOLORB, color.getBlue());
        backgroundColorLabel.setBackground(color);
    }

    protected int getBackgroundTransparency() {
        return myPref.getInt(myName + BACKGROUNDTRANSPARENCY, 255);
    }

    protected void setBackgroundTransparency(final int value) {
        myPref.putInt(myName + BACKGROUNDTRANSPARENCY, value);
    }

    protected void initMyComponent() {
//        System.out.println("Drawable initMyComponent");
        myComponent = new JPanel(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        myComponent.add(new JLabel(FOREGROUND), gbc);
        gbc.gridx++;
        foregroundColorLabel = new JPanel();
        foregroundColorLabel.setBackground(getForegroundColor(false));
        myComponent.add(foregroundColorLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        foregroundColorButton = new JButton(FOREGROUNDCOLOR);
        foregroundTransparencyButton = new JButton(FOREGROUNDTRANSPARENCY);
        myComponent.add(foregroundColorButton, gbc);
        gbc.gridx++;
        myComponent.add(foregroundTransparencyButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
//        myComponent.add(new JLabel(BACKGROUND), gbc);
        gbc.gridx++;
        backgroundColorLabel = new JPanel();
        backgroundColorLabel.setBackground(getBackgroundColor());
//        myComponent.add(backgroundColorLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        backgroundColorButton = new JButton(BACKGROUNDCOLOR);
        backgroundTransparencyButton = new JButton(BACKGROUNDTRANSPARENCY);
//        myComponent.add(backgroundColorButton, gbc);
        gbc.gridx++;
//        myComponent.add(backgroundTransparencyButton, gbc);

        gbc.gridy++;
        gbc.gridx = 0;

        ActionListener buttonListener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                src = (JButton) e.getSource();
                if (src == foregroundColorButton || src == backgroundColorButton) {
                    JButton localsrc = src;
                    JColorChooser cc = new JColorChooser(localsrc == foregroundColorButton ? getForegroundColor(false) : getBackgroundColor());
                    JDialog dialog = JColorChooser.createDialog(myComponent,
                            "Change " + (localsrc == foregroundColorButton ? FOREGROUNDCOLOR : BACKGROUNDCOLOR), true, cc,
                            this, null);
                    dialog.setVisible(true);
                    dialog.toFront();
                    if (localsrc == foregroundColorButton) {
                        setForegroundColor(cc.getColor());
                    } else {
                        setBackgroundColor(cc.getColor());
                    }
                } else if (src == foregroundTransparencyButton || src == backgroundTransparencyButton) {
                    customOptionPane = new JOptionPane();

                    JSlider slider = new JSlider();
                    slider.setMajorTickSpacing(50);
                    slider.setPaintTicks(true);
                    slider.setPaintLabels(true);

                    slider.setMinimum(0);
                    slider.setMaximum(255);
                    slider.setValue(src == foregroundTransparencyButton ? getForegroundTransparency() : getBackgroundTransparency());

                    ChangeListener changeListener = new ChangeListener() {

                        public void stateChanged(ChangeEvent changeEvent) {
                            JSlider theSlider = (JSlider) changeEvent.getSource();
                            if (!theSlider.getValueIsAdjusting()) {
                                customOptionPane.setInputValue(new Integer(theSlider.getValue()));
                                if (DrawableElement.this.src == foregroundTransparencyButton) {
                                    setForegroundTransparency(Integer.valueOf(customOptionPane.getInputValue().toString()));
                                } else {
                                    setBackgroundTransparency(Integer.valueOf(customOptionPane.getInputValue().toString()));
                                }
                            }
                        }
                    };
                    slider.addChangeListener(changeListener);

                    customOptionPane.setInputValue(new Integer(slider.getValue()));
                    customOptionPane.setMessage(new Object[]{src == foregroundTransparencyButton ? FOREGROUNDTRANSPARENCY : BACKGROUNDTRANSPARENCY + ": ", slider});
                    customOptionPane.setMessageType(JOptionPane.PLAIN_MESSAGE);
                    customOptionPane.setOptionType(JOptionPane.YES_OPTION);
                    JDialog dialog = customOptionPane.createDialog(myComponent, src == foregroundTransparencyButton ? FOREGROUNDTRANSPARENCY : BACKGROUNDTRANSPARENCY);

                    //slider.getSelectionModel().addChangeListener(this);
                    //slider.addChangeListener(this);
//                    I.toBack();
                    dialog.setVisible(true);
                    dialog.toFront();
//                    persistWindow();
                }
            }
        };
        foregroundColorButton.addActionListener(buttonListener);
        foregroundTransparencyButton.addActionListener(buttonListener);
        backgroundColorButton.addActionListener(buttonListener);
        backgroundTransparencyButton.addActionListener(buttonListener);
    }

    @Override
    public void persist(int num) {
        super.persist(num);
        setForegroundColor(foreGroundColor);
    }

    @Override
    public synchronized JComponent getChangeComponent() {
//        System.out.println("Drawable getChangeComponent");
        return super.getChangeComponent();
    }
}
