/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.graphichelpers;

import de.mylifesucks.oss.ncsimulator.gui.InfoPanel;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class JTextAreaFromFile extends JTextArea {

    public JTextAreaFromFile(final String path) {
        super(path);

        try {
            InputStream is = getClass().getResourceAsStream(path);

            String content = new Scanner(is, "UTF-8").useDelimiter("\\Z").next();

            // replace comment-header
            content = content.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)", "");
            content = content.trim();

            is.close();
            setText(content);

        } catch (IOException ex) {
            setText(ex.getMessage());
            Logger.getLogger(InfoPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
