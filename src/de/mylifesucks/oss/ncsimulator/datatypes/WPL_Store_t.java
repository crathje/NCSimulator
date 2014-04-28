/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.mylifesucks.oss.ncsimulator.datatypes;

import java.awt.GridBagConstraints;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author frankblumenberg
 */
public class WPL_Store_t extends c_int implements Observer {

    public u8 Index;
    public u8 res1;
    public u8 OverwriteFile;         // 0 = no, 1 = yes
    public u8 reserved[] = new u8[7];
    public c_string Name;

    public WPL_Store_t(int idx) {
        super();
        allAttribs = new LinkedList<c_int>();

        Index = new u8(idx + " Index");
        res1 = new u8(idx + " res1");
        OverwriteFile = new u8(idx + " OverwriteFile", 1);

        Name = new c_string(idx + " Name", 12, "");
        for (int i = 0; i < reserved.length; i++) {
            reserved[i] = new u8(idx + "" + i);
        }

        allAttribs.add(Index);
        allAttribs.add(res1);
        allAttribs.add(OverwriteFile);
        allAttribs.add(Name);
        allAttribs.addAll(Arrays.asList(reserved));

        for (c_int c : allAttribs) {
            c.addObserver(this);
        }

    }

    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers();
    }

    public synchronized void addToPanel(JComponent panel, GridBagConstraints gbc) {
        if (nameLabel == null) {
            initComponent();
            res1.initComponent();
            OverwriteFile.initComponent();
            Name.initComponent();
        }

        gbc.gridx = 0;
        panel.add(new JLabel("Has File"), gbc);
        gbc.gridx++;
        panel.add(res1.valueField, gbc);
        gbc.gridx++;
        panel.add(Name.getNameLabel(), gbc);
        gbc.gridx++;
        panel.add(Name.valueField, gbc);
        gbc.gridy++;
    }

}
