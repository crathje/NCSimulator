/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.gui.datawindow.elements;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.geom.AffineTransform;
import java.util.prefs.Preferences;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class ShiftTransformation extends Transformation {

    c_intElement valuex, valuey;

    public ShiftTransformation(final Preferences rootPref, final String name) {
        super(rootPref, name);
        valuex = new c_intElement(myPref, name + "valuex");
        valuey = new c_intElement(myPref, name + "valuey");
        initMyComponent();
    }

    @Override
    public AffineTransform getAffineTransform(final Dimension d) {
        return AffineTransform.getTranslateInstance(valuex.getValue(), valuey.getValue());
    }

    @Override
    protected void extraPersist() {
        valuex.persist(myName + "valuex");
        valuey.persist(myName + "valuey");
    }

    @Override
    protected void initMyComponent() {
        myComponent = new JPanel();
        myComponent.setLayout(new BoxLayout(myComponent, BoxLayout.Y_AXIS));
        myComponent.add(new JLabel("value to take as x:"));
        myComponent.add(valuex.getChangeComponent());
        myComponent.add(new JLabel("value to take as y:"));
        myComponent.add(valuey.getChangeComponent());
    }
}

