/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.gui.datawindow.elements;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.util.prefs.Preferences;
import javax.swing.JPanel;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class RotateTransformation extends Transformation {

    c_intElement value;

    public RotateTransformation(final Preferences rootPref, final String name) {
        super(rootPref, name);
        value = new c_intElement(myPref, name + "value");
        initMyComponent();
    }

    @Override
    public AffineTransform getAffineTransform(final Dimension d) {
        return AffineTransform.getRotateInstance(deg2rad(value.getValue()), d.width / 2.0, d.height / 2.0);
    }

    @Override
    protected void extraPersist() {
        value.persist(myName + "value");
    }

    @Override
    protected void initMyComponent() {
        myComponent = new JPanel();
        myComponent.add(value.getChangeComponent(), BorderLayout.CENTER);
    }
}
