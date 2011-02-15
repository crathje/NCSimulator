/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.gui.datawindow.elements;

import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage;
import de.mylifesucks.oss.ncsimulator.datatypes.c_int;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;
import java.util.prefs.Preferences;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class c_intElement {

    private String domain = "";
    public final static String DATADOMAIN = "Data Domain";
    private String valName = "";
    public final static String DATAVALNAME = "Data Value Name";
    private long offset = 0;
    private JTextField offsetField;
    public static final String ADDOFFSET = "An Offset value to add before computing the value";
    private double scaleFactor = 1.0;
    private JTextField scaleField;
    public static final String SCALEFACTOR = "Scalefactor to multiply value with";
    //
    public static final String CONSTALIAS = "<<< CONST0 >>>";
    //
    private String myName;
    //
    private Preferences locPref;
    //
    private DefaultListModel listModel;
    private JList list;
    private JComponent myComponent;

    public c_intElement(final Preferences locPref, final String name) {
        myName = getClass().getSimpleName() + name;
        this.locPref = locPref;
        domain = locPref.get(myName + DATADOMAIN, domain);
        valName = locPref.get(myName + DATAVALNAME, valName);
        offset = locPref.getLong(myName + ADDOFFSET, offset);
        scaleFactor = locPref.getDouble(myName + SCALEFACTOR, scaleFactor);

        initMyComponent();
    }

    public String getMyname() {
        return myName;
    }

    public void persist(final String name) {
        myName = getClass().getSimpleName() + name;
        try {
            offset = Long.valueOf(offsetField.getText());
        } catch (NumberFormatException nfe) {
            //
        }
        try {
            scaleFactor = Double.valueOf(scaleField.getText());
        } catch (NumberFormatException nfe) {
            //
        }
        if (list.getSelectedValue() != null) {
            valName = list.getSelectedValue().toString();
            locPref.put(myName + DATADOMAIN, domain);
            locPref.put(myName + DATAVALNAME, valName);
            locPref.putLong(myName + ADDOFFSET, offset);
            locPref.putDouble(myName + SCALEFACTOR, scaleFactor);
        }
    }

    private LinkedList<c_int> getAllPossibleProducers() {
        LinkedList<c_int> params = DataStorage.naviData.getList();
        params.addAll(DataStorage.FCDebugOut.getList());
        return params;
    }

    private LinkedList<String> getAllAliases() {
        LinkedList<String> ret = new LinkedList<String>();
        ret.add(CONSTALIAS);
        for (c_int c : getAllPossibleProducers()) {
            ret.add(c.toString());
        }
        return ret;
    }

    public JComponent getChangeComponent() {
        for (int i = 0; i < listModel.getSize(); i++) {
            if (valName.equals(listModel.get(i).toString())) {
                list.setSelectedIndex(i);
                break;
            }
        }
        return myComponent;
    }

    public long getRawValue() {
        if (CONSTALIAS.equals(valName)) {
            return 0;
        }
        for (c_int c : getAllPossibleProducers()) {
            if (valName.equals(c.toString())) {
//                System.out.println("Taking value from: " + c);
                return c.value;
            }
        }
        return Long.MIN_VALUE;
    }

    public double getValue() {
        return ((double) (getRawValue() + offset)) * scaleFactor;
    }

    private void initMyComponent() {
        myComponent = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        listModel = new DefaultListModel();
        for (String c : getAllAliases()) {
            listModel.addElement(c);
        }
        list = new JList(listModel);

        gbc.gridheight = 4;
        myComponent.add(new JScrollPane(list), gbc);

        gbc.gridheight = 1;
        gbc.gridx++;
        myComponent.add(new JLabel(ADDOFFSET), gbc);
        gbc.gridy++;
        offsetField = new JTextField(String.valueOf(offset));
        myComponent.add(offsetField, gbc);
        gbc.gridy++;
        myComponent.add(new JLabel(SCALEFACTOR), gbc);
        gbc.gridy++;
        scaleField = new JTextField(String.valueOf(scaleFactor));
        myComponent.add(scaleField, gbc);

//        gbc.gridy++;
//        gbc.gridx--;
//        gbc.gridwidth = 2;
//        JButton saveButton = new JButton("save");
//        saveButton.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//
//                persist();
//            }
//        });
//        myComponent.add(saveButton, gbc);

//        gbc.gridy++;
//        JButton fooButton = new JButton("fff");
//        fooButton.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                System.out.println(getValue());
//            }
//        });
//        myComponent.add(fooButton, gbc);
    }
}
