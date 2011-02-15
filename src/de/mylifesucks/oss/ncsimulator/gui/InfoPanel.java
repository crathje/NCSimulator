/**
 *
 * Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 * admiralcascade@gmail.com
 * Licensed under: Creative Commons / Non Commercial / Share Alike
 * http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.gui;

import de.mylifesucks.oss.graphichelpers.JTextAreaFromFile;
import de.mylifesucks.oss.ncsimulator.Main;
import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import org.jdesktop.swingx.JXBusyLabel;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class InfoPanel extends JPanel {

    public static final String checkButtonString = "Check for latest Version";
    public static final String youhavenewest = "You already have the newest Version!";
    JButton latestButton;
    Runnable checker;
    JXBusyLabel busyLabel;
    JLabel checkText;
    boolean checked = false;

    public InfoPanel() {
        super(new BorderLayout());
        Box verticalBox = Box.createVerticalBox();

        Box center = Box.createHorizontalBox();
        center.add(Box.createHorizontalGlue());
        center.add(new JLabel(new ImageIcon(DataStorage.mainLogo.getImage().getScaledInstance(
                DataStorage.mainLogo.getIconWidth() / 2,
                DataStorage.mainLogo.getIconHeight() / 2,
                Image.SCALE_SMOOTH))));
        center.add(Box.createHorizontalGlue());
        verticalBox.add(center);



        latestButton = new JButton(checkButtonString);

        latestButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (!checked) {
                    busyLabel.setBusy(true);
                    DataStorage.executors.submit(checker);
                    //          latestButton.setMinimumSize(latestButton.getSize());
                    //                 latestButton.setText("Checking...");
                } else if (!latestButton.isEnabled()) {
                    // we have newest
                } else {
                    if (Desktop.isDesktopSupported()) {
                        Desktop d = Desktop.getDesktop();
                        if (d != null) {
                            try {
                                d.browse(new URI("http://www.mylifesucks.de/oss/NCSimulator/NCSimulator-latest.zip"));
                            } catch (Exception ex) {
                            }
                        }
                    }
                }
            }
        });

        checkText = new JLabel("                      ");
        checkText.setSize(300, checkText.getSize().height);

        busyLabel = new JXBusyLabel();
        center = Box.createHorizontalBox();
        center.add(Box.createHorizontalGlue());
        center.add(latestButton);
        center.add(busyLabel);
        center.add(Box.createHorizontalGlue());
        verticalBox.add(center);

        center = Box.createHorizontalBox();
        center.add(Box.createHorizontalGlue());
        center.add(checkText);
        center.add(Box.createHorizontalGlue());
        verticalBox.add(center);

        checker = new Runnable() {

            public void run() {
                try {
                    URL url = new URL("http://www.mylifesucks.de/oss/NCSimulator/NCSimulator-latest.txt");
                    URLConnection connection = url.openConnection();
                    InputStream is = connection.getInputStream();
                    String newest = new Scanner(is, "UTF-8").useDelimiter("\\Z").next();

                    //System.out.println(newest);
                    if (newest.equals("NCSimulator-" + Main.version)) {
                        //System.out.println("Current");
                        //latestButton.setText(youhavenewest);
//                        latestButton.setEnabled(false);
                        checkText.setText(youhavenewest);
                    } else {
                        checkText.setText(newest);
                    }
                    is.close();
//                    checked = true;
                } catch (Exception ex) {
                    //Logger.getLogger(InfoPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                busyLabel.setBusy(false);
            }
        };


//        checkThread.start();

        JTextArea jta = new JTextArea("Some hacked Java GUI to simulate the OSD (and some other) output from the NC\n"
                + "A lot of bugs might be here since it is heavily untested stuff but somehow helpfull for debugging own Applications that require data from an MK.\n"
                + "\n"
                + "Open Source:\n"
                + "\tLicensed under: Creative Commons / Non Commercial / Share Alike\n"
                + "\thttp://creativecommons.org/licenses/by-nc-sa/2.0/de/\n"
                + "\tSources available at: https://github.com/crathje/NCSimulator\n"
                +"\n"
                + "This Software uses:\n"
                + "\tRXTX which is licensed under the LGPL v2.1 and available from http://rxtx.qbang.org/\n"
                + "\tSwingX which is licensed under the LGPL and available from http://www.swinglabs.org/\n"
                + "\n"
                + "Credits to:\n"
                + "\tMarcus -LiGi- Bueschleb for the Encode/Decode java parts I borrowed from DUBWise\n"
                + "\tHolger Buss & Ingo Busker for the MikroKopter-project\n"
                + "");
//        jta.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        jta.setBackground(this.getBackground());
        //jta.setWrapStyleWord(true);
        //jta.setAutoscrolls(true);
        jta.setBackground(this.getBackground());
        jta.setEditable(false);
        jta.setLineWrap(true);
        //jta.setColumns(20);
        /*jta.setSize(new Dimension(DataStorage.mainLogo.getIconWidth(), DataStorage.mainLogo.getIconHeight()));
        jta.setPreferredSize(jta.getSize());
        jta.setMinimumSize(jta.getSize());
        jta.setMaximumSize(jta.getSize());*/
        center = Box.createHorizontalBox();
        center.add(Box.createHorizontalGlue());
        Box vertical = Box.createVerticalBox();

        JTabbedPane tabbed = new JTabbedPane();
        tabbed.add("General", new JScrollPane(jta));
        //vertical.add(new JScrollPane(jta));




        JTextArea changeLog = new JTextAreaFromFile("/CHANGE.LOG");
        changeLog.setEditable(false);
        changeLog.setBackground(getBackground());
        changeLog.setLineWrap(true);
        tabbed.add("Change Log", new JScrollPane(changeLog));


        vertical.add(tabbed);
        Box links = Box.createHorizontalBox();
        links.add(new LinkLabel("NCSimulator", "http://www.mylifesucks.de/oss/NCSimulator/"));
        links.add(Box.createHorizontalGlue());
        links.add(new LinkLabel("NCSimulator latest", "http://www.mylifesucks.de/oss/NCSimulator/NCSimulator-latest.zip"));
        links.add(Box.createHorizontalGlue());
        links.add(new LinkLabel("Thread in MK-Forum", "http://forum.mikrokopter.de/topic-20435.html"));
        links.add(Box.createHorizontalGlue());

        links.add(new LinkLabel("MikroKopter", "http://www.mikrokopter.de/"));
        links.add(Box.createHorizontalGlue());
        links.add(new LinkLabel("DUBwise", "http://www.mikrokopter.de/ucwiki/DUBwise"));
        vertical.add(links);
        center.add(vertical);
        center.add(Box.createHorizontalGlue());


//        center.add(jb);


        center.add(Box.createHorizontalGlue());
        verticalBox.add(center);

        //Desktop.getDesktop().browse(null);


        add(verticalBox, BorderLayout.CENTER);
    }
}
