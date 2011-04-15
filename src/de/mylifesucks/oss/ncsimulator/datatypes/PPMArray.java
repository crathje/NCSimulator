/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.datatypes;

import java.util.LinkedList;

/**
 * PPMArray representing PPM_in
 * adapted from: http://svn.mikrokopter.de/filedetails.php?repname=FlightCtrl&path=/tags/V0.82a/rc.h
 * 
 * @author Claas Anders "CaScAdE" Rathje
 */
public class PPMArray extends c_int {

    public s16 PPMArray[] = new s16[26];

    public PPMArray() {
        allAttribs = new LinkedList<c_int>();
        for (int i = 0; i < PPMArray.length; i++) {
            s16 c = new s16("Kanalbelegung " + i);
            PPMArray[i] = c;
            allAttribs.add(c);
        }

//        PPMArray[13].setValue(-125, false);
//        PPMArray[16].setValue(125, false);

    }
}
