/*
 *  Copyright (C) 2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JPanel;
import javax.media.CannotRealizeException;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.bean.playerbean.MediaPlayer;
import javax.swing.JButton;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class VideoWindow extends JPanel {
//TODO: CHECK FOR JMF AND SKIP ALL THE SHIT IF NON-EXISTEND
//    private Player mediaPlayer;
//    private JButton startStop;
//    public static final String STARTMEDIAPLAYER = "start";
//    public static final String STOPMEDIAPLAYER = "stop";
//
//    public VideoWindow(String camURL) {
//        super();
//        this.setLayout(new BorderLayout());
//
//        Vector devices = CaptureDeviceManager.getDeviceList(null);
//        System.out.println("Number of capture devices: " + devices.size());
//        for (int n = 0; n < devices.size(); n++) {
//            CaptureDeviceInfo info = (CaptureDeviceInfo) devices.elementAt(n);
//            System.out.println(info.toString());
//        }
//
//        Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, true);
//
//        try {
////            LogSystem.CLog("Devices: " + CaptureDeviceManager.getDeviceList(null));
//
//
//            MediaLocator ml = new MediaLocator(camURL); // "vfw://" standard URL
//            mediaPlayer = Manager.createRealizedPlayer(ml);
//
//
//
//            Component video = mediaPlayer.getVisualComponent();
//
//            if (video != null) {
//                this.add(video, BorderLayout.CENTER);
//
//            }
//
//
//
//            mediaPlayer.setRate(25.0f);
//            mediaPlayer.start();
//
//            JPanel controlPanel = new JPanel(new FlowLayout());
//
//            controlPanel.add(mediaPlayer.getControlPanelComponent());
////            for (Control c : mediaPlayer.getControls()) {
////                System.out.println(c);
////                try {
////                    controlPanel.add(c.getControlComponent());
////                } catch (Exception e) {
////                    System.err.println("D'oh: " + e);
////                }
////            }
//
//
//
//            startStop = new JButton(mediaPlayer.getState() == MediaPlayer.Started ? STOPMEDIAPLAYER : STARTMEDIAPLAYER);
//            startStop.addActionListener(new ActionListener() {
//
//                public void actionPerformed(ActionEvent e) {
//                    if (mediaPlayer.getState() == MediaPlayer.Started) {
//                        mediaPlayer.stop();
//                        startStop.setText(STARTMEDIAPLAYER);
//                    } else {
//                        mediaPlayer.start();
//                        startStop.setText(STOPMEDIAPLAYER);
//                    }
//                }
//            });
//            controlPanel.add(startStop);
//
//            this.add(controlPanel, BorderLayout.SOUTH);
//        } catch (NoPlayerException noPlayerException) {
////            LogSystem.addLog(LogSystem.getMessage("webcamViewError001"));
//            System.err.println("no webcam found // no JMF framework");
//        } catch (CannotRealizeException cre) {
////            LogSystem.addLog(LogSystem.getMessage("webcamViewError002"));
////            LogSystem.addLog("Could not realize media player.");
//            System.err.println("Could not realize media player.");
//        } catch (IOException ie) {
////            LogSystem.addLog(LogSystem.getMessage("webcamViewError003"));
////            LogSystem.addLog("Error reading from the source.");
//            System.err.println("Error reading from the source.");
//        }
//        setBounds(0, 0, 600, 450);
//    }
//
//    public void close() {
//        if (this.mediaPlayer != null && this.mediaPlayer.getState() == MediaPlayer.Started) {
//            this.mediaPlayer.close();
//        }
//    }
}
