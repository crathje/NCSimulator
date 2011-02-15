/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.gui.datawindow.elements;

import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.util.prefs.Preferences;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public abstract class Transformation extends AbstractElement {

    public Transformation(final Preferences rootPref, final String name) {
        super(rootPref, name);
    }

    public abstract AffineTransform getAffineTransform(final Dimension d);

    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
}
