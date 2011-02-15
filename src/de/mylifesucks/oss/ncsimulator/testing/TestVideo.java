/*
 *  Copyright (C) 2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.testing;

import de.mylifesucks.oss.ncsimulator.datatypes.s16;
import de.mylifesucks.oss.ncsimulator.gui.VideoWindow;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class TestVideo {

    public static void main(String[] args) throws IOException {
        JFrame foo = new JFrame("video test");
        foo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        VideoWindow v = new VideoWindow("vfw://0");
//        foo.getContentPane().add(v);

        foo.pack();
        foo.setSize(640, 480);
        foo.setLocationRelativeTo(null);
        foo.setVisible(true);
    }
}

