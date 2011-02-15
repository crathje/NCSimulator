/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.datatypes;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 8bit unsigned int which is used as bit-flag
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class u8Flags extends u8 {

    String[] labels;
    ArrayList<JCheckBox> checkboxes;
    JPanel checkboxpanel;

    public u8Flags() {
        super();
    }

    public u8Flags(String name) {
        super(name);
    }

    public u8Flags(String name, String[] labels) {
        super(name);
        this.labels = labels;
    }

    @Override
    public void setValue(long value, boolean notice) {
        if (checkboxes == null) {
            initComponent();
        }
        super.setValue(value, true);
        for (JCheckBox c : checkboxes) {
            int val = (1 << checkboxes.indexOf(c));
            //System.out.println("checking: " + val + " in " + value + " : " + (val & value));
            c.setSelected((val & value) != 0);
        }
    }

    @Override
    protected void initComponent() {

        valueField = new JTextField("" + value);
        valueField.setEditable(false);
        valueField.setPreferredSize(new Dimension(c_int.LABELWIDTH, valueField.getPreferredSize().height));
        valueField.setSize(valueField.getPreferredSize());
        valueField.setMaximumSize(valueField.getPreferredSize());
        valueField.setMinimumSize(valueField.getPreferredSize());


        ItemListener il = new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                if (e.getSource() instanceof JCheckBox) {
                    JCheckBox c = (JCheckBox) e.getSource();
                    //System.out.println(c.getState() + "" + (1 << checkboxes.indexOf(c)));
                    int val = (1 << checkboxes.indexOf(c));
                    if (c.isSelected()) {
                        value |= val;
                    } else {
                        value &= ~(val);
                    }
                    valueField.setText("" + value);

                }
            }
        };



        checkboxpanel = new JPanel(new GridLayout(2, 4));

        checkboxes = new ArrayList<JCheckBox>(length);

        for (String label : labels) {
            JCheckBox c = new JCheckBox(label);
            c.addItemListener(il);
            checkboxes.add(c);
            checkboxpanel.add(c);
        }


    }

    @Override
    public synchronized void addToPanel(JComponent panel, GridBagConstraints gbc) {

        if (checkboxes == null) {
            initComponent();
        }
        gbc.gridx = 0;
        panel.add(getNameLabel(), gbc);
        gbc.gridx++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(valueField, gbc);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx++;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(checkboxpanel, gbc);
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridy++;
    }
}

