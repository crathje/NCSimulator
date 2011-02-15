/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.gui;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JLabel;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class LinkLabel extends JLabel {

    URI uri;
    Cursor oldCursor;

    public LinkLabel(String text, String url) {
        super(text);
        try {
            uri = new URI(url);
        } catch (URISyntaxException ex) {
        }

        setToolTipText(url);

        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                oldCursor = getCursor();
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(oldCursor);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (Desktop.isDesktopSupported()) {
                    Desktop d = Desktop.getDesktop();
                    if (d != null) {
                        try {
                            d.browse(uri);
                        } catch (IOException ex) {
                        }
                    }
                }
            }
        });

    }
}
