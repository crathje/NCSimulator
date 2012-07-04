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
import de.mylifesucks.oss.ncsimulator.datatypes.Motor_t;
import de.mylifesucks.oss.ncsimulator.datatypes.BLData_t;
import de.mylifesucks.oss.ncsimulator.gui.datawindow.DataWindowPanel;
import de.mylifesucks.oss.ncsimulator.datatypes.Waypoint_t;
import de.mylifesucks.oss.ncsimulator.datatypes.c_int;
import de.mylifesucks.oss.ncsimulator.datatypes.u16;

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
import de.mylifesucks.oss.ncsimulator.protocol.TcpComm;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.NumberFormat;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * The main Panel
 * @author Claas Anders "CaScAdE" Rathje
 */
public class MainPanel extends JPanel {

    public static final String SERIALPORT = "Serial Port";
    public static final String TCPPORT = "Tcp Port";
    JButton start;
    JComboBox ports;
    JButton startTcp;
    JTextField tcpPort;
    JSlider timeSlider;
    CoordVizualizer cv;
    JTabbedPane tabbed;
    public JCheckBox isUSB;
    public static final String requestOSD = "Request the NC-OSD dataset";
    public static final String stopOSD = "Stop requesting the NC-OSD dataset";
    public static final String requestDEBUG = "Request the FC-Debug dataset";
    public static final String stopDEBUG = "Stop requesting the FC-Debug dataset";

    public class ValueDocument extends PlainDocument {

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str.length() == 0 || str.equals("")) {
                super.insertString(offs, str, a);
            } else if (str != null) {
                try {
                    Long.valueOf(str);
                    super.insertString(offs, str, a);
                } catch (NumberFormatException ex) {
                    return;
                }
            }
        }

        @Override
        public void remove(int offs, int len) throws BadLocationException {
            super.remove(offs, len);
        }
    }

    public MainPanel() {
        super(new BorderLayout());

        tabbed = new JTabbedPane();
        this.add(tabbed, BorderLayout.CENTER);


        Box configBox = Box.createVerticalBox();

        Box center = Box.createHorizontalBox();
        center.add(Box.createHorizontalGlue());

        ports = new JComboBox(SerialComm.getPorts().keySet().toArray());
        center.add(ports);

        String savedPort = DataStorage.preferences.get(SERIALPORT, "");
        ports.setSelectedItem(savedPort);

        isUSB = new JCheckBox("Is USB adapter?");

        center.add(isUSB);
        start = new JButton("start");

        start.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {
                    ports.setEditable(false);
                    ports.setEnabled(false);
                    start.setEnabled(false);
                    DataStorage.preferences.put(SERIALPORT, ports.getSelectedItem().toString());
                    DataStorage.serial = new SerialComm(ports.getSelectedItem().toString(), isUSB.isSelected());
                    DataStorage.encoder = new Encode(DataStorage.serial.getOutputStream());
                    DataStorage.sendThread = new SendThread("Serial send Thread");
                    DataStorage.sendThread.start();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Start Error", JOptionPane.ERROR_MESSAGE);
                    ports.setEditable(false);
                    ports.setEnabled(false);
                    start.setEnabled(false);
                }
            }
        });

        center.add(start);
        center.add(Box.createHorizontalGlue());
        configBox.add(center);

        tcpPort = new JTextField();
        tcpPort.setPreferredSize(new Dimension(80, tcpPort.getPreferredSize().height));
        tcpPort.setSize(tcpPort.getPreferredSize());
        tcpPort.setMaximumSize(tcpPort.getPreferredSize());
        tcpPort.setMinimumSize(tcpPort.getPreferredSize());
        tcpPort.setDocument(new ValueDocument());
        tcpPort.setText("64400");

        center = Box.createHorizontalBox();
        center.add(Box.createHorizontalGlue());
        center.add(tcpPort);
        startTcp = new JButton("start");
        startTcp.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {
                    tcpPort.setEditable(false);
                    tcpPort.setEnabled(false);
                    startTcp.setEnabled(false);
                    DataStorage.preferences.put(TCPPORT, tcpPort.getText());

                    DataStorage.serial = new TcpComm(Integer.parseInt(tcpPort.getText()));
                    DataStorage.encoder = new Encode(DataStorage.serial.getOutputStream());
                    DataStorage.sendThread = new SendThread("Serial send Thread");
                    DataStorage.sendThread.start();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Start Error", JOptionPane.ERROR_MESSAGE);
                    tcpPort.setEditable(false);
                    tcpPort.setEnabled(false);
                    startTcp.setEnabled(false);
                }
            }
        });



        center.add(startTcp);
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
            DataStorage.naviData.Version.value = 5;
        }

        tabbed.addTab("Log", DataStorage.logPanel);

        JPanel osdPanel = new JPanel(new GridBagLayout());
        GridBagConstraints osdgbc = new GridBagConstraints();
        osdgbc.gridy++;
        DataStorage.naviData.addToPanel(osdPanel, osdgbc);
        JScrollPane osdScrollpane = new JScrollPane(osdPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabbed.addTab("NC OSD Values", osdScrollpane);


        JPanel blPanel = new JPanel(new GridBagLayout());
        GridBagConstraints oblgbc = new GridBagConstraints();
        oblgbc.gridy++;
        for (BLData_t bld : DataStorage.bldata_t) {
            oblgbc.gridy++;
            oblgbc.gridx = 0;
            oblgbc.gridwidth = GridBagConstraints.REMAINDER;
            blPanel.add(new JLabel("BLCtrl #"+bld.Index.value), oblgbc);
            oblgbc.gridy++;
            oblgbc.gridwidth = 1;
            bld.addToPanel(blPanel, oblgbc);
            
        }
        JScrollPane blScrollpane = new JScrollPane(blPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabbed.addTab("BLCtrl", blScrollpane);

//        motorgcgbc.gridy++;
//        motor.addToPanel(motorPanel, motorgcgbc);

        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        listPane.add(new JLabel("Label1"));
        listPane.add(new JLabel("Label1"));

        for (int i = 0; i < DataStorage.motors.length; i++) {
            Motor_t motor = new Motor_t(i);

            DataStorage.motors[i] = motor;

            JPanel motorPanel = new JPanel(new GridBagLayout());
            GridBagConstraints motorgcgbc = new GridBagConstraints();
            motorgcgbc.gridy++;
            motor.addToPanel(motorPanel, motorgcgbc);
            listPane.add(motorPanel);

            listPane.add(new JLabel("Label1"));
        }
        listPane.add(new JLabel("Label1"));
        listPane.add(new JLabel("Label1"));

        JScrollPane motorFCScrollpane = new JScrollPane(listPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabbed.addTab("MotorData", motorFCScrollpane);

        DataStorage.clearWP();
        
        JPanel wpListPane = new JPanel();
        wpListPane.setLayout(new BoxLayout(wpListPane, BoxLayout.PAGE_AXIS));
        wpListPane.add(new JLabel("Label1"));
        wpListPane.add(new JLabel("Label1"));
        for (int i = 0; i < 10; i++) {
            
            Waypoint_t wp = DataStorage.waypointList[i];
            
            JPanel motorPanel = new JPanel(new GridBagLayout());
            GridBagConstraints motorgcgbc = new GridBagConstraints();
            motorgcgbc.gridy++;
            wp.addToPanel(motorPanel, motorgcgbc);
            wpListPane.add(motorPanel);
            
            wpListPane.add(new JLabel("Label1"));
        }
        wpListPane.add(new JLabel("Label1"));
        wpListPane.add(new JLabel("Label1"));

        JScrollPane waypointScrollpane = new JScrollPane(wpListPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabbed.addTab("Waypoints", waypointScrollpane);
        
//        JTabbedPane waipointsPanel = new JTabbedPane();
//        waipointsPanel.setLayout(new BoxLayout(waipointsPanel, BoxLayout.PAGE_AXIS));
//
//        for (int i = 0; i < 12; i++) {
//            JPanel setPanel = new JPanel();
//            GridBagConstraints wpdgbc = new GridBagConstraints();
//            wpdgbc.gridy++;
//            
//            JLabel label = new JLabel("Motor");
//            setPanel.add(label);
//            setPanel.setBackground(i%20==0?Color.yellow:Color.cyan);
//
////            Waypoint_t wp = DataStorage.waypointList.get(i);
////            wp.addToPanel(setPanel, paramgbc);
////
////            JScrollPane paramScrPanel = new JScrollPane(setPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//            
//            waipointsPanel.add("Waypoint " + (i + 1), setPanel);
//        }
//        tabbed.addTab("Motor", waipointsPanel);
//
//
//
//        JPanel wpPanel = new JPanel(new GridBagLayout());
//        GridBagConstraints wpdgbc = new GridBagConstraints();
//        wpdgbc.gridy++;
//        for (int i = 0; i < Waypoint_t.waypointList.length; i++) {
//            wpPanel.add(new JLabel("Waypoint #" + i), wpdgbc);
//            wpdgbc.gridy++;
//
//            JPanel wpePanel = new JPanel(new GridBagLayout());
//            GridBagConstraints wpedgbc = new GridBagConstraints();
//
//            wpePanel.add(new JLabel("Data Waypoint #" + i), wpedgbc);
//            wpedgbc.gridx=0;
//            wpedgbc.gridy++;
//            Waypoint_t.waypointList[i].addToPanel(wpePanel, wpedgbc);
//
//            wpPanel.add(wpePanel, wpdgbc);
//            wpdgbc.gridy++;
//        }
//        //DataStorage.naviData.addToPanel(osdPanel, osdgbc);
//        JScrollPane wpScrollpane = new JScrollPane(wpPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        tabbed.addTab("Waipoints", wpScrollpane);


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
        DataStorage.FCversion.SWMinor.value = 84;
        DataStorage.FCversion.SWPatch.value = 0; // a
        DataStorage.FCversion.ProtoMajor.value = 11;
        DataStorage.FCversion.ProtoMinor.value = 0;

        DataStorage.NCversion.SWMajor.value = 0;
        DataStorage.NCversion.SWMinor.value = 24;
        DataStorage.NCversion.SWPatch.value = 1; // a
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
