/**
 *
 * Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 * admiralcascade@gmail.com
 * Licensed under: Creative Commons / Non Commercial / Share Alike
 * http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.gui;

import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage;
import de.mylifesucks.oss.ncsimulator.gui.datawindow.DataWindow;
import de.mylifesucks.oss.ncsimulator.gui.datawindow.DataWindowPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import de.mylifesucks.oss.ncsimulator.protocol.Encode;
import de.mylifesucks.oss.ncsimulator.protocol.SendThread;
import de.mylifesucks.oss.ncsimulator.protocol.SerialComm;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/**
 * The main Panel
 * @author Claas Anders "CaScAdE" Rathje
 */
public class MainPanel extends JPanel {

    public static final String SERIALPORT = "Serial Port";
    JButton start;
    JComboBox ports;
    JSlider timeSlider;
    CoordVizualizer cv;
    JTabbedPane tabbed;
    public JCheckBox isUSB;
    public static final String requestOSD = "Request the NC-OSD dataset";
    public static final String stopOSD = "Stop requesting the NC-OSD dataset";
    public static final String requestDEBUG = "Request the FC-Debug dataset";
    public static final String stopDEBUG = "Stop requesting the FC-Debug dataset";

    public MainPanel() {
        super(new BorderLayout());

        tabbed = new JTabbedPane();
        this.add(tabbed, BorderLayout.CENTER);


        Box configBox = Box.createVerticalBox();

        Box center = Box.createHorizontalBox();
        center.add(Box.createHorizontalGlue());
        Vector<String> portlist = SerialComm.getPorts();
        ports = new JComboBox(portlist);
        center.add(ports);

        String savedPort = DataStorage.preferences.get(SERIALPORT, "");
        ports.setSelectedItem(savedPort);

        isUSB = new JCheckBox("Is USB adapter?");

        center.add(isUSB);
        start = new JButton("start");

        start.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try{
                ports.setEditable(false);
                ports.setEnabled(false);
                start.setEnabled(false);
                DataStorage.preferences.put(SERIALPORT, ports.getSelectedItem().toString());
                DataStorage.serial = new SerialComm(ports.getSelectedItem().toString(), isUSB.isSelected());
                DataStorage.encoder = new Encode(DataStorage.serial.getOutputStream());
                DataStorage.sendThread = new SendThread("Serial send Thread");
                DataStorage.sendThread.start();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"Start Error",JOptionPane.ERROR_MESSAGE);
                    ports.setEditable(false);
                    ports.setEnabled(false);
                    start.setEnabled(false);
                }
            }
        });

        center.add(start);
        center.add(Box.createHorizontalGlue());
        configBox.add(center);

        /*timeSlider = new JSlider(JSlider.HORIZONTAL,
        100, 1000, (int) sleeptime);
        timeSlider.setPaintLabels(true);
        timeSlider.setPaintTicks(true);
        timeSlider.setMajorTickSpacing(100);
        timeSlider.setMinorTickSpacing(10);
        timeSlider.addChangeListener(new ChangeListener() {

        public void stateChanged(ChangeEvent e) {
        if (e.getSource() == timeSlider) {
        sleeptime = timeSlider.getValue();
        }
        }
        });
        configBox.add(new JLabel("Sleep time between debug out"));
        configBox.add(timeSlider);*/


        center = Box.createHorizontalBox();
        center.add(Box.createHorizontalGlue());
        JButton intervalForce = new JButton("Force OSD Data output 100ms");
        intervalForce.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                DataStorage.naviData.requestTime = 100;
            }
        });
        center.add(intervalForce);
        center.add(Box.createHorizontalGlue());
        configBox.add(center);


        center = Box.createHorizontalBox();
        center.add(Box.createHorizontalGlue());
        final JButton requestButton = new JButton(requestOSD);
        requestButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (requestButton.getText().equals(requestOSD)) {
                    DataStorage.requestOSDtime = 500;
                    requestButton.setText(stopOSD);


                } else {
                    DataStorage.requestOSDtime = 0;
                    requestButton.setText(requestOSD);


                }
            }
        });
        center.add(requestButton);
        center.add(Box.createHorizontalGlue());
        configBox.add(center);

        //############
        center = Box.createHorizontalBox();
        center.add(Box.createHorizontalGlue());
        final JButton requestDebugButton = new JButton(requestDEBUG);
        requestDebugButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (requestDebugButton.getText().equals(requestDEBUG)) {
                    DataStorage.requestDEBUGtime = 500;
                    requestDebugButton.setText(stopDEBUG);
                } else {
                    DataStorage.requestDEBUGtime = 0;
                    requestDebugButton.setText(requestDEBUG);
                }
            }
        });
        center.add(requestDebugButton);
        center.add(Box.createHorizontalGlue());
        configBox.add(center);
        //############


        JPanel config = new JPanel();
        tabbed.addTab("Config", config);
        config.add(configBox);

        center = Box.createHorizontalBox();
        center.add(Box.createHorizontalGlue());

        final JButton save = new JButton("Save values");
        save.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == save) {
                    DataStorage.serializePool();


                }
            }
        });
        center.add(save);

        final JButton load = new JButton("Load values");
        load.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == load) {
                    DataStorage.loadPool();


                }
            }
        });
        center.add(load);

        final JButton delte = new JButton("Delte saved values");
        delte.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == delte) {
                    DataStorage.deltePool();


                }
            }
        });
        center.add(delte);
        center.add(Box.createHorizontalGlue());
        configBox.add(center);


        // this can save a lot of trouble when interfacing with the MK-Tool :)
        if (DataStorage.naviData.Version.value == 0) {
            DataStorage.naviData.Version.value = 4;
        }

        tabbed.addTab("Log", DataStorage.logPanel);

        JPanel osdPanel = new JPanel(new GridBagLayout());
        GridBagConstraints osdgbc = new GridBagConstraints();
        osdgbc.gridy++;
        DataStorage.naviData.addToPanel(osdPanel, osdgbc);
        JScrollPane osdScrollpane = new JScrollPane(osdPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabbed.addTab("NC OSD Values", osdScrollpane);



        JPanel debugFCValPanel = new JPanel(new GridBagLayout());
        GridBagConstraints debuggcgbc = new GridBagConstraints();
        debuggcgbc.gridy++;
        DataStorage.FCDebugOut.addToPanel(debugFCValPanel, debuggcgbc);
        JScrollPane debugFCScrollpane = new JScrollPane(debugFCValPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabbed.addTab("FC Debug Values", debugFCScrollpane);

        JPanel debugNCValPanel = new JPanel(new GridBagLayout());
        GridBagConstraints debugncgbc = new GridBagConstraints();
        debugncgbc.gridy++;
        DataStorage.NCDebugOut.addToPanel(debugNCValPanel, debugncgbc);
        JScrollPane debugNCScrollpane = new JScrollPane(debugNCValPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabbed.addTab("NC Debug Values", debugNCScrollpane);


        JTabbedPane paramPanel = new JTabbedPane();
        //this.add(tabbed, BorderLayout.CENTER);


        for (int i = 0; i
                < DataStorage.paramset.length; i++) {
            JPanel setPanel = new JPanel(new GridBagLayout());
            GridBagConstraints paramgbc = new GridBagConstraints();
            paramgbc.gridy++;
            DataStorage.paramset[i].addToPanel(setPanel, paramgbc);
            JScrollPane paramScrPanel = new JScrollPane(setPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            paramPanel.add("Set " + DataStorage.paramset[i].index, paramScrPanel);


        }
        tabbed.addTab("Parameter", paramPanel);


        JPanel Val3dPanel = new JPanel(new GridBagLayout());
        GridBagConstraints val3dgbc = new GridBagConstraints();
        val3dgbc.gridy++;
        DataStorage.data3d_t.addToPanel(Val3dPanel, val3dgbc);
        JScrollPane val3dScrollpane = new JScrollPane(Val3dPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabbed.addTab("3D Values", val3dScrollpane);


        DataStorage.FCversion.SWMajor.value = 0;
        DataStorage.FCversion.SWMinor.value = 82;
        DataStorage.FCversion.SWPatch.value = 0; // a
        DataStorage.FCversion.ProtoMajor.value = 11;
        DataStorage.FCversion.ProtoMinor.value = 0;

        DataStorage.NCversion.SWMajor.value = 0;
        DataStorage.NCversion.SWMinor.value = 22;
        DataStorage.NCversion.SWPatch.value = 0; // a
        DataStorage.NCversion.ProtoMajor.value = 11;
        DataStorage.NCversion.ProtoMinor.value = 0;

        DataStorage.MK3version.SWMajor.value = 0;
        DataStorage.MK3version.SWMinor.value = 23;
        DataStorage.MK3version.SWPatch.value = 0;
        DataStorage.MK3version.ProtoMajor.value = 11;
        DataStorage.MK3version.ProtoMinor.value = 0;



        JPanel versionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints versiongbc = new GridBagConstraints();
        versiongbc.gridy++;
        versiongbc.gridx = 0;
        versiongbc.gridwidth = GridBagConstraints.REMAINDER;
        versionPanel.add(new JLabel("FC"), versiongbc);
        versiongbc.gridwidth = 1;
        versiongbc.gridy++;
        versiongbc.gridx = 0;
        DataStorage.FCversion.addToPanel(versionPanel, versiongbc);
        versiongbc.gridy++;
        versiongbc.gridx = 0;
        versiongbc.gridwidth = GridBagConstraints.REMAINDER;
        versionPanel.add(new JLabel("NC"), versiongbc);
        versiongbc.gridwidth = 1;
        versiongbc.gridy++;
        versiongbc.gridx = 0;
        DataStorage.NCversion.addToPanel(versionPanel, versiongbc);
        versiongbc.gridy++;
        versiongbc.gridx = 0;
        versiongbc.gridwidth = GridBagConstraints.REMAINDER;
        versionPanel.add(new JLabel("MK3Mag"), versiongbc);
        versiongbc.gridwidth = 1;
        versiongbc.gridy++;
        versiongbc.gridx = 0;
        DataStorage.MK3version.addToPanel(versionPanel, versiongbc);
        JScrollPane versionScroll = new JScrollPane(versionPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tabbed.addTab("Versions", versionScroll);


        JPanel lcdpanel = new JPanel(new GridBagLayout());
        GridBagConstraints lcdgbc = new GridBagConstraints();
        lcdgbc.gridy++;
        DataStorage.lcddata.addToPanel(lcdpanel, lcdgbc);
        JScrollPane lcdScrollpane = new JScrollPane(lcdpanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabbed.addTab("LCD Text", lcdScrollpane);

        cv = DataStorage.coordVizualizer;
        tabbed.addTab("Coord Map", cv);



        JScrollPane dataWindowPanelgbcScroll = new JScrollPane(new DataWindowPanel(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabbed.addTab("DataWindows", dataWindowPanelgbcScroll);

        //tabbed.addTab("Information", new VideoWindow("vfw://0"));

        tabbed.addTab("Information", new InfoPanel());


        tabbed.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                if (e.getSource() == tabbed) {
                    if (tabbed.getSelectedComponent() == cv) {
                        cv.update();


                    }
                }
            }
        });






        this.add(DataStorage.statusBar, BorderLayout.SOUTH);

    }
}


