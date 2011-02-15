/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.gui.datawindow.elements;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.prefs.Preferences;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class BarElement extends DrawableElement {

    public BarElement(final Preferences nodePref, final String name) {
        super(nodePref, name);
    }

    @Override
    public void drawTo(Graphics2D g2d, Dimension dim) {
        g2d.setColor(getForegroundColor(true));

        Line2D line = new Line2D.Double(-dim.width, dim.height / 2.0, 2 * dim.width, dim.height / 2.0);
        g2d.draw(line);
    }

    protected void extraPersist() {
    }
}
