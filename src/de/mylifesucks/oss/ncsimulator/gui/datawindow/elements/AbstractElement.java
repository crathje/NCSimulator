/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.gui.datawindow.elements;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.JComponent;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public abstract class AbstractElement {

    public final static String NODENAME = "Elements";
    public final static String CLASSTYPE = "CLASSTYPE";
    protected Preferences myPref;
    protected JComponent myComponent;
    protected String myName;

    public AbstractElement(final Preferences rootNode, final String myName) {
        myPref = rootNode.node(NODENAME);
        this.myName = myName;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + myName;
    }

    public void persist(final int num) {
        // remove old Node
//        System.out.println("removing old: " + myName + CLASSTYPE + " from: " + myPref.absolutePath());
        myPref.remove(myName + CLASSTYPE);

        myName = String.valueOf(num);

        myPref.put(myName + CLASSTYPE, getClass().getSimpleName());
//        System.out.println("saved: " + myName + getClass().getSimpleName() + " to: " + myPref.absolutePath());

        extraPersist();
    }

    public void dispose() {
        myPref.remove(myName + CLASSTYPE);
//        System.out.println("removing: " + myName + CLASSTYPE + " from: " + myPref.absolutePath());
        try {
            myPref.flush();
        } catch (BackingStoreException ex) {
            Logger.getLogger(AbstractElement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    abstract protected void extraPersist();

    public static AbstractElement elementFromString(final Preferences rootpref, final String className, final int i) {
        if (className.equals(BarElement.class.getSimpleName())) {
            return new BarElement(rootpref, String.valueOf(i));
        } else if (className.equals(StringElement.class.getSimpleName())) {
            return new StringElement(rootpref, String.valueOf(i));
        } else if (className.equals(RotateTransformation.class.getSimpleName())) {
            return new RotateTransformation(rootpref, String.valueOf(i));
        } else if (className.equals(ShiftTransformation.class.getSimpleName())) {
            return new ShiftTransformation(rootpref, String.valueOf(i));
        }
        return null;
    }

    public static LinkedList<AbstractElement> load(final Preferences rootpref) {
        LinkedList<AbstractElement> ret = new LinkedList<AbstractElement>();
        int i = 0;
        Preferences nodePref = rootpref.node(NODENAME);

//        System.out.println("Loading from: " + nodePref.absolutePath());
        while (!CLASSTYPE.equals(nodePref.get(i + CLASSTYPE, CLASSTYPE))) {

            String className = nodePref.get(i + CLASSTYPE, CLASSTYPE);
//            System.out.println("Found: " + i + CLASSTYPE + " :" + className);

            AbstractElement elem = elementFromString(rootpref, className, i);
            if (elem != null) {
                ret.add(elem);
            }

            i++;
        }

        return ret;
    }

    protected abstract void initMyComponent();

    public synchronized JComponent getChangeComponent() {
//        System.out.println("Abstract getChangeComponent");
        if (myComponent == null) {
            initMyComponent();
        }
        return myComponent;
    }
}
