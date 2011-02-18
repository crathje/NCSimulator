/**
 *
 * Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 * admiralcascade@gmail.com
 * Licensed under: Creative Commons / Non Commercial / Share Alike
 * http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator;

import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import de.mylifesucks.oss.ncsimulator.gui.MainPanel;
import de.mylifesucks.oss.ncsimulator.gui.datawindow.DataWindow;
import de.mylifesucks.oss.ncsimulator.gui.tray.SimTray;
import de.mylifesucks.oss.ncsimulator.protocol.CommunicationBase;
import de.mylifesucks.oss.ncsimulator.protocol.SerialComm;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.geom.Rectangle2D;
import javax.swing.UIManager;

/**
 * Dummy Main class to start Software and show splashScreen
 * @author Claas Anders "CaScAdE" Rathje
 */
public class Main extends JFrame {

    public static MainPanel vPanel;
    private static SplashScreen mySplash;
    private static Rectangle2D.Double splashTextArea;
    private static Graphics2D splashGraphics;
    private static Rectangle2D.Double splashProgressArea;
    public static final String mainFrameW = "MainFrame Width";
    public static final String mainFrameH = "MainFrame Height";
    public static final String mainFramePX = "MainFrame Position X";
    public static final String mainFramePY = "MainFrame Position Y";
    public static final String title = "NC Simulator by CaScAdE";
    public static final String version = "2011XXXX-XXXX";

    Main() {
        super(title + " Version: " + version);

        setLayout(new BorderLayout());
        vPanel = new MainPanel();

        setIconImage(DataStorage.mainLogo.getImage());
        this.add(vPanel, BorderLayout.CENTER);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                DataStorage.preferences.putInt(mainFrameW, getWidth());
                DataStorage.preferences.putInt(mainFrameH, getHeight());
                DataStorage.preferences.putInt(mainFramePX, getLocation().x);
                DataStorage.preferences.putInt(mainFramePY, getLocation().y);
//                try {
//                    String fileName = "foo.xml";
//                    //DataStorage.preferences.importPreferences(new BufferedInputStream(new FileInputStream(fileName)));
//
//                    //DataStorage.preferences.exportNode(new BufferedOutputStream(new FileOutputStream(fileName)));
//                    DataStorage.preferences.exportSubtree(new BufferedOutputStream(new FileOutputStream(fileName)));
//                } catch (FileNotFoundException ex) {
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IOException ex) {
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (BackingStoreException ex) {
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
////                } catch (InvalidPreferencesFormatException ex) {
////                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                }

                System.exit(0);
            }
        });

        this.pack();


        this.setSize(new Dimension((int) Math.min(getWidth(),
                Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 100),
                Math.min(getHeight(), 500)));
        this.setMinimumSize(getSize());

        //this.setSize(new Dimension(1280, 1200));
        this.setLocationRelativeTo(null);
        //this.setResizable(false);



        this.setSize(DataStorage.preferences.getInt(mainFrameW, getWidth()),
                DataStorage.preferences.getInt(mainFrameH, getHeight()));

        this.setLocation(DataStorage.preferences.getInt(mainFramePX, getLocation().x),
                DataStorage.preferences.getInt(mainFramePY, getLocation().y));

        SimTray.mainFrame = this;
        SimTray.getInstance();
    }

    /**
     * Prepare the global variables for the other splash functions
     */
    private static void initSplashScreen() {
        mySplash = SplashScreen.getSplashScreen();
        if (mySplash != null) {
            int fontsize = 14;
            splashTextArea = new Rectangle2D.Double(133, 179, 151, fontsize + 3);
            splashProgressArea = new Rectangle2D.Double(55, 236, 282 - 55, 7);

            splashGraphics = mySplash.createGraphics();

            splashGraphics.setFont(new Font("Dialog", Font.PLAIN, fontsize));

            drawSplashMessage("Starting");
            drawSplashProgressBar(0);
        }
    }

    /**
     * Display a (very) basic progress bar
     * @param pct how much of the progress bar to display 0-100
     */
    public static void drawSplashProgressBar(int pct) {
        if (mySplash != null && mySplash.isVisible()) {

            // erase the old one
//            splashGraphics.setPaint(Color.LIGHT_GRAY);
//            splashGraphics.fill(splashProgressArea);

            // draw an outline
            splashGraphics.setPaint(Color.BLACK);
            splashGraphics.draw(splashProgressArea);

            // Calculate the width corresponding to the correct percentage
            int x = (int) splashProgressArea.getMinX();
            int y = (int) splashProgressArea.getMinY();
            int wid = (int) splashProgressArea.getWidth();
            int hgt = (int) splashProgressArea.getHeight();

            int doneWidth = Math.round(pct * wid / 100.f);
            doneWidth = Math.max(0, Math.min(doneWidth, wid - 1));  // limit 0-width

            // fill the done part one pixel smaller than the outline
            splashGraphics.setPaint(Color.RED);
            splashGraphics.fillRect(x + 1, y + 1, doneWidth, hgt - 1);

            // make sure it's displayed
            mySplash.update();
        }
    }

    /**
     * Display text in status area of Splash.  Note: no validation it will fit.
     * @param str - text to be displayed
     */
    public static void drawSplashMessage(String str) {
        if (mySplash != null && mySplash.isVisible()) {   // important to check here so no other methods need to know if there


            // erase the last status text
            splashGraphics.setPaint(Color.WHITE);
            splashGraphics.fill(splashTextArea);

            // draw the text
            splashGraphics.setPaint(Color.BLACK);
            splashGraphics.setFont(splashGraphics.getFont().deriveFont(12.0f));
            splashGraphics.drawString(str, (int) (splashTextArea.getX() + 5), (int) (splashTextArea.getY() + 12));

            // make sure it's displayed
            mySplash.update();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        initSplashScreen();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // when fails, we stick to the default one
        }

        // initialize dataStorage
        drawSplashProgressBar(20);
        drawSplashMessage("Initializing DataStorage");
        DataStorage.getInstance();

        // detect ports
        drawSplashProgressBar(40);
        drawSplashMessage("Detecting Serial Ports");
        SerialComm.getPorts();

        // buid main Frame
        drawSplashProgressBar(60);
        drawSplashMessage("Initializing MainFrame");
        Main main = new Main();

        // initialize dataWindows
        drawSplashProgressBar(75);
        drawSplashMessage("Initializing DataWindows");
        DataWindow.init();

        drawSplashProgressBar(99);
        drawSplashMessage("Nearly done");



        drawSplashProgressBar(100);
        drawSplashMessage("Done");

        main.setVisible(true);
        main.toFront();


        // before starting
        if (mySplash != null) {// check if we really had a spash screen
            if (mySplash.isVisible()) {
                mySplash.close();   // if so we're now done with it
            }
        }


    }
}
