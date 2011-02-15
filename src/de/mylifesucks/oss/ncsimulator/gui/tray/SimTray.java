/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.gui.tray;

import de.mylifesucks.oss.ncsimulator.Main;
import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage;
import de.mylifesucks.oss.ncsimulator.gui.datawindow.DataWindow;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class SimTray {

    public static SimTray instance;
    PopupMenu popup;
    TrayIcon trayIcon;
    SystemTray tray;
    public static Main mainFrame;
    MenuItem ShowHidePanel;

    public static SimTray getInstance() {
        if (instance == null) {
            instance = new SimTray();
        }
        return instance;
    }
    public final static String SHOWALLDATAWINDOWS = "Show Datawindows";
    public final static String HIDEALLDATAWINDOWS = "Hide Datawindows";
    public final static String HIDEMAINWINDOW = "Hide Main Window";
    public final static String SHOWMAINWINDOW = "Show Main Window";
    MenuItem showHideDatawindows;

    private SimTray() {

        popup = new PopupMenu();
        //popup.setFont(new Font("Dialog", Font.PLAIN, 10));
        if (!SystemTray.isSupported()) {
            System.err.println("Sorry, no System tray available.");
            return;
        }

        tray = SystemTray.getSystemTray();

        trayIcon = new TrayIcon(DataStorage.mainLogo.getImage().getScaledInstance(tray.getTrayIconSize().width, tray.getTrayIconSize().height, Image.SCALE_FAST));

        ShowHidePanel = new MenuItem(HIDEMAINWINDOW);

        showHideDatawindows = new MenuItem(SHOWALLDATAWINDOWS);

        //Menu commandMenu = new Menu("Command");
//        MenuItem Send = new MenuItem("Send current command");
        MenuItem Exit = new MenuItem("Exit");



        Exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });

        showHideDatawindows.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (showHideDatawindows.getLabel().equals(SHOWALLDATAWINDOWS)) {
                    showHideDatawindows.setLabel(HIDEALLDATAWINDOWS);
                } else {
                    showHideDatawindows.setLabel(SHOWALLDATAWINDOWS);
                }
                for (DataWindow dw : DataStorage.dataWindows) {
                    dw.setVisible(!showHideDatawindows.getLabel().equals(SHOWALLDATAWINDOWS));
                }
            }
        });



        ShowHidePanel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (mainFrame.isVisible()) {
                    mainFrame.setVisible(false);
                    ShowHidePanel.setLabel(SHOWMAINWINDOW);
                } else {
                    mainFrame.setVisible(true);
                    ShowHidePanel.setLabel(HIDEMAINWINDOW);
                }
                //mainFrame.setVisible(true);
            }
        });

        /*Hide.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        mainFrame.setVisible(false);
        }
        });*/




        //popup.add(Hide);
        popup.add(ShowHidePanel);
        popup.addSeparator();
        popup.add(showHideDatawindows);
        popup.addSeparator();
        popup.add(Exit);
        trayIcon.setPopupMenu(popup);
        trayIcon.setToolTip(mainFrame.getTitle() + " ready for action...");

        try {
            tray.add(trayIcon);
        } catch (Exception e) {
            System.err.println("TrayIcon could not be added.\n" + e);
        }
    }
}

